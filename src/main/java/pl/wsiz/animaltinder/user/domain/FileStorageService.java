package pl.wsiz.animaltinder.user.domain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.wsiz.animaltinder.auth.exception.BusinessException;
import pl.wsiz.animaltinder.auth.exception.ErrorMessage;
import pl.wsiz.animaltinder.user.api.dto.PictureDto;

import javax.annotation.PreDestroy;

@Service
public class FileStorageService {

    public static final String FILE_STORAGE_LOCATION = "./uploads/files";
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(Environment env) {
        this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", FILE_STORAGE_LOCATION))
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public PictureDto storeFile(MultipartFile file, PictureType pictureType, Long id) {
        String fileName = determinePictureName(id, pictureType);

        try {
            if (fileName.contains("..")) {
                throw new RuntimeException(
                        "Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return new PictureDto(fileName);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String[] fileNameParts = fileName.split("\\.");

        return fileNameParts[fileNameParts.length - 1];
    }

    @PreDestroy
    public void deleteUploads() throws IOException {
        FileUtils.deleteDirectory(new File(FILE_STORAGE_LOCATION));
    }

    public byte[] getPicture(Long id, PictureType pictureType) {
        String fileName = determinePictureName(id, pictureType);
        byte[] result;
        try{
            result = Files.readAllBytes(Paths.get(FILE_STORAGE_LOCATION + "/" + fileName));
        }catch (Exception e){
            throw new BusinessException(ErrorMessage.NO_PICTURE_AVAILABLE);
        }
        return result;
    }

    private String determinePictureName(Long id, PictureType pictureType) {
        if (pictureType.equals(PictureType.USER)) {
            return "user" + id + ".jpeg";
        } else {
            return "animal" + id + ".jpeg";
        }
    }
}