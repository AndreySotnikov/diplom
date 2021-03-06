package diplom.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import diplom.entity.Characteristic;
import diplom.entity.File;
import diplom.entity.Revision;
import diplom.entity.Subscription;
import diplom.repository.*;
import diplom.util.HTTPExecutor;
import diplom.util.JinqSource;
import diplom.util.SubscriptionEvent;
import diplom.util.SubscriptionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vova on 12.03.16.
 */
@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    RevisionRepository revisionRepository;

    @Autowired
    CharacteristicRepository characteristicRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    AttributeRepository attributeRepository;

    @Autowired
    LoginService loginService;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private JinqSource source;

    @Value("${dir}")
    String fileDir;

    @Autowired
    HTTPExecutor httpExecutor;

    @Autowired
    ObjectMapper objectMapper;

    private List<SubscriptionListener> listener = new ArrayList<>();

    public void addListener(SubscriptionListener l){
        listener.add(l);
    }

    public void removeListener(SubscriptionListener l){
        listener.remove(l);
    }

    public File getFile(int id) {
        return fileRepository.findOne(id);
    }

    public Revision getRevision(int id) {
        return revisionRepository.findOne(id);
    }

    public boolean fullSave(MultipartFile inputFile, int fileId,
                            String username, String description,
                            String sessionKey) {
        File file = getFile(fileId);
        Revision revision = addRevision(fileId, username, description);
        if (revision == null)
            return false;
        return saveFile(inputFile, revision.getPath());
    }

    public boolean fullSaveFromScracth(MultipartFile inputFile, String filename,
                                       String username, String sessionkey,
                                       String description, String attrs, String name) {
        String originalFilename = inputFile.getOriginalFilename();
        String extension = null;
        int index = originalFilename.lastIndexOf(".");
        if (index != -1)
            extension = originalFilename.substring(index);
        if (extension != null)
            name += extension;
        File file = addFile(filename, description, name, attrs, sessionkey);
        if (file == null)
            return false;
        Revision revision = addRevision(file.getId(), username, description);
        if (revision == null)
            return false;

        return saveFile(inputFile, revision.getPath());
    }

    public boolean addNewRevision(MultipartFile inputFile, String sessionKey,
                                  String description, int fileId) {
        Revision revision = addRevision(fileId, loginService.getLoginBySession(sessionKey), description);
        return saveFile(inputFile, revision.getPath());
    }

    public boolean saveFile(MultipartFile file, String fileName) {
        try {
            byte[] bytes = file.getBytes();

            int index = fileName.lastIndexOf("/");
            String parsedDir = fileName.substring(0, index);

            String parsedName = fileName.substring(index);

            // Creating the directory to store file
            java.io.File dir = new java.io.File(fileDir + parsedDir);
            if (!dir.exists())
                dir.mkdirs();


            // Create the file on server
            java.io.File serverFile = new java.io.File(dir.getAbsolutePath() + parsedName);
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public void setAttrs(File file, String attrs) {
        Map<String, String> map = null;
        try {
            map = objectMapper.readValue(attrs, Map.class);
        } catch (IOException ex) {
        }
        try {
            for (String attrId: map.keySet()) {
                Characteristic tmp = new Characteristic();
                tmp.setcfile(file);
                tmp.setAttribute(attributeRepository.findOne(Integer.valueOf(attrId)));
                tmp.setValue(map.get(attrId));
                characteristicRepository.save(tmp);
            }
        } catch (Throwable ex) {

        }
    }

    @Transactional
    public File addFile(String fileName, String description, String name,
                        String attrs, String sessionKey) {
        File file = new File();
        file.setName(name);
        file.setDescription(description);
        file.setDirectory(fileName);
        String result = httpExecutor.execute("/entity/addfile", "?sessionKey=" + sessionKey);
        try {
            int entityId = Integer.valueOf(result);
            file.setEntityId(entityId);
        } catch (Throwable e) {
            return null;
        }
        file = fileRepository.save(file);
        setAttrs(file, attrs);
        return file;
    }

    public Revision addRevision(int fileId, String username, String description) {
        File file = fileRepository.findOne(fileId);
        if (file == null)
            return null;
        Revision revision = new Revision();
        revision.setUsername(username);
        revision.setFile(file);
        SubscriptionEvent event = new SubscriptionEvent();
        event.setEntityName(file.getName());
        event.setLogin(username);
        event.setEventType("revision_update");
        if (listener!=null && !listener.isEmpty())
            listener.forEach(l -> l.handle(event));
        revision.setDescription(description);
        revision = revisionRepository.save(revision);
        revision.setPath("/" + fileId + "/" + revision.getId());
        return revisionRepository.save(revision);
    }

    public  List<File> getFilesForUser(String sessionKey) {
        String result = httpExecutor.execute("/entity/allfiles", "?sessionKey=" + sessionKey);
        ObjectMapper mapper = new ObjectMapper();
        List<Integer> fileids = null;
        try {
            fileids = mapper.readValue(result, List.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return fileRepository.getByIDs(fileids);
    }

    @Transactional
    public boolean deleteFile(String sessionKey, int id) {
        //// TODO check access
        File file = fileRepository.findOne(id);
        List<Characteristic> chars = file.getCharacteristics();
        List<Revision> revs = file.getRevisions();
        List<Subscription> subs = file.getSubscriptions();
        if (chars != null)
            for (Characteristic chr: chars) {
                if (chr.getSubscription() != null)
                    subscriptionRepository.delete(chr.getSubscription().getId());
                characteristicRepository.delete(chr.getId());
            }
        if (revs != null)
            for (Revision rev: revs)
                revisionRepository.delete(rev.getId());
        if (subs != null)
            for (Subscription sub: subs)
                subscriptionRepository.delete(sub.getId());
        fileRepository.delete(id);
        //// TODO remove entity to
        return true;
    }

    public List<Revision> getRevisions(int id) {
        List<Revision> revisions = source.streamAll(em, Revision.class)
                .where(revision -> revision.getFile().getId() == id).toList();
        return revisions;
    }

    public List<File> searchFiles(String sessionKey, String attrs) {
        List<File> result = new ArrayList<File>();
        Map<String, String> map = null;
        try {
            map = objectMapper.readValue(attrs, Map.class);
            List<Characteristic> chrs = new ArrayList<Characteristic>();
            characteristicRepository.findAll().forEach(chrs::add);
            for (String attrId : map.keySet()) {
                int att = Integer.valueOf(attrId);
                String chr = map.get(attrId);
                for (Characteristic charact: chrs) {
                    if (charact.getAttribute().getId() != att)
                        continue;
                    if (charact.getValue().indexOf(chr) == -1)
                        continue;
                    if (!checkAccessToFile(sessionKey, charact.getcfile(), "READ"))
                        continue;
                    result.add(charact.getcfile());
                }
            }
        } catch (Throwable ex) {

        }
        return result;
    }

    public boolean checkAccessToFile(String sessionKey, File file, String rightType) {
        String result = httpExecutor.execute("/rights/checkAccess",
                "?sessionKey=" + sessionKey +
                "&entityId=" + file.getEntityId() +
                "&rightType=" + rightType);
        return Boolean.parseBoolean(result);
    }

}
