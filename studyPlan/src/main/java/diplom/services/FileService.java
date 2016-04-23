package diplom.services;

import diplom.entity.Characteristic;
import diplom.entity.File;
import diplom.entity.Revision;
import diplom.entity.Subscription;
import diplom.repository.CharacteristicRepository;
import diplom.repository.FileRepository;
import diplom.repository.RevisionRepository;
import diplom.repository.SubscriptionRepository;
import diplom.util.HTTPExecutor;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.std.StdArraySerializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    LoginService loginService;

    @Value("${admin.name}")
    String fileDir;

    @Autowired
    HTTPExecutor httpExecutor;

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
        String fileName = getFileName(fileId, revision.getId());
        return saveFile(inputFile, fileName);
    }

    private String getFileName(int fileId, int revId) {
        return "file:" + fileId + "revision:" + revId;
    }

    public boolean fullSaveFromScracth(MultipartFile inputFile, String filename,
                                       String username, String sessionkey,
                                       String description, String attrs, String name) {
        File file = addFile(filename, description, name, attrs, sessionkey);
        if (file == null)
            return false;
        Revision revision = addRevision(file.getId(), username, description);
        if (revision == null)
            return false;

        return saveFile(inputFile, revision.getId()+"");
    }

    public boolean saveFile(MultipartFile file, String fileName) {
        try {
            byte[] bytes = file.getBytes();

            // Creating the directory to store file
            java.io.File dir = new java.io.File(fileDir);
            if (!dir.exists())
                dir.mkdirs();


            // Create the file on server
            java.io.File serverFile = new java.io.File(dir.getAbsolutePath()
                    + java.io.File.separator + fileName);
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

    public List<Characteristic> parseAttrs(String attrs) {
        List<Characteristic> result = new ArrayList<>();

        if (result.isEmpty())
            return null;
        return result;
    }

    public File addFile(String fileName, String description, String name,
                        String attrs, String sessionKey) {
        File file = new File();
        file.setName(name);
        file.setDescription(description);
        file.setDirectory(fileName);
        file.setCharacteristics(parseAttrs(attrs));
        String result = httpExecutor.execute("/entity/addfile", "?sessionKey=" + sessionKey);
        try {
            int entityId = Integer.valueOf(result);
            file.setEntityId(entityId);
        } catch (Throwable e) {
            return null;
        }
        return fileRepository.save(file);
    }

    public Revision addRevision(int fileId, String username, String description) {
        File file = fileRepository.findOne(fileId);
        if (file == null)
            return null;
        Revision revision = new Revision();
        revision.setUsername(username);
        revision.setFile(file);
        revision.setDescription(description);
        return revision;
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

}
