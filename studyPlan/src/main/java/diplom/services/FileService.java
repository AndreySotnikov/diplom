package diplom.services;

import diplom.entity.File;
import diplom.entity.Revision;
import diplom.repository.CharacteristicRepository;
import diplom.repository.FileRepository;
import diplom.repository.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by vova on 12.03.16.
 */
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    RevisionRepository revisionRepository;

    @Autowired
    CharacteristicRepository characteristicRepository;

    @Autowired
    LoginService loginService;

    @Value("${admin.name}")
    String fileDir;

    public File getFile(int id) {
        return fileRepository.findOne(id);
    }

    public Revision getRevision(int id) {
        return revisionRepository.findOne(id);
    }

    public boolean fullSave(MultipartFile inputFile, int fileId,
                            String username, String description) {
        Revision revision = addRevision(fileId, username, description);
        if (revision == null)
            return false;

        return saveFile(inputFile, revision.getId()+"");
    }

    public boolean fullSaveFromScracth(MultipartFile inputFile, String filename,
                                       String username,
                                       String description, List<Integer> chars) {
        File file = addFile(filename, description, chars);
        if (file == null)
            return false;
        Revision revision = addRevision(file.getId(), username, description);
        if (revision == null)
            return false;

        return saveFile(inputFile, revision.getId()+"");
    }

    public boolean saveFile(MultipartFile file, String name) {
        try {
            byte[] bytes = file.getBytes();

            // Creating the directory to store file
            java.io.File dir = new java.io.File(fileDir);
            if (!dir.exists())
                dir.mkdirs();


            // Create the file on server
            java.io.File serverFile = new java.io.File(dir.getAbsolutePath()
                    + java.io.File.separator + name);
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

    public File addFile(String name, String description, List<Integer> chars) {
        File file = new File();
        file.setName(name);
        file.setDescription(description);
        file.setCharacteristics(characteristicRepository.getChars(chars));
        return fileRepository.save(file);
    }

    public Revision addRevision(int fileId, String username, String description) {
        File file = fileRepository.findOne(fileId);
        if (file == null)
            return null;
        Revision revision = new Revision();
        revision.setUsername(username);
        revision.setDescription(description);
        revision.setFile(file);
        return revision;
    }
}
