package com.MustCloud.Cloud.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MustCloud.Cloud.Users.User;
import com.MustCloud.Cloud.Users.UserService;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserService userService;

    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    public List<File> findFilesByUserId(Integer userId) {
        return fileRepository.findByUserUserId(userId);
    }

    public List<File> findAllFiles() {
        return fileRepository.findAll();
    }

    public File findFileById(Integer fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }

    public void deleteFile(Integer fileId) {
        fileRepository.deleteById(fileId);
    }

    public File shareFile(Integer fileId, String recipientEmail) throws IOException {
        // Kaynak dosyayı bul
        File sourceFile = findFileById(fileId);
        if (sourceFile == null) {
            throw new RuntimeException("Dosya bulunamadı");
        }

        // Alıcı kullanıcıyı bul
        User recipient = userService.findUserByEmail(recipientEmail);
        if (recipient == null) {
            throw new RuntimeException("Alıcı kullanıcı bulunamadı");
        }

        try {
            // Yeni bir dosya nesnesi oluştur
            File sharedFile = new File();
            sharedFile.setUser(recipient);
            sharedFile.setFileName(sourceFile.getFileName());
            sharedFile.setFileType(sourceFile.getFileType());
            sharedFile.setFileSize(sourceFile.getFileSize());
            
            // Dosyayı fiziksel olarak kopyala
            Path sourcePath = Paths.get(sourceFile.getStoragePath());
            String newFileName = System.currentTimeMillis() + "_" + sourceFile.getFileName();
            Path targetPath = Paths.get("uploads/" + newFileName);
            Files.createDirectories(targetPath.getParent());
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            
            sharedFile.setStoragePath(targetPath.toString());
            
            return fileRepository.save(sharedFile);
        } catch (IOException e) {
            throw new IOException("Dosya kopyalama sırasında hata oluştu: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Dosya paylaşımı sırasında beklenmeyen bir hata oluştu: " + e.getMessage(), e);
        }
    }
}