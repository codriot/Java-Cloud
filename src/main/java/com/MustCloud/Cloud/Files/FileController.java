package com.MustCloud.Cloud.Files;

import com.MustCloud.Cloud.Folders.Folder;
import com.MustCloud.Cloud.Folders.FolderService;
import com.MustCloud.Cloud.Users.User;
import com.MustCloud.Cloud.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private FolderService folderService;

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    @Async
    public CompletableFuture<ResponseEntity<File>> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Integer userId) throws IOException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                User user = userService.findUserById(userId);
                if (user == null) {
                    throw new IllegalArgumentException("User not found");
                }

                // Dosyayı yükleme dizinine kaydet
                Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getBytes());

                // Dosya bilgilerini veritabanına kaydet
                File newFile = new File();
                newFile.setUser(user);
                newFile.setFileName(file.getOriginalFilename());
                newFile.setFileType(file.getContentType());
                newFile.setFileSize(file.getSize());
                newFile.setStoragePath(filePath.toString());
                File savedFile = fileService.saveFile(newFile);
                return ResponseEntity.ok(savedFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Integer fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/download/{fileId}")
    public CompletableFuture<ResponseEntity<Resource>> downloadFile(@PathVariable Integer fileId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                File file = fileService.findFileById(fileId);
                if (file == null) {
                    throw new IllegalArgumentException("File not found");
                }

                Path filePath = Paths.get(file.getStoragePath());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
                zipOutputStream.putNextEntry(new ZipEntry(file.getFileName()));
                Files.copy(filePath, zipOutputStream);
                zipOutputStream.closeEntry();
                zipOutputStream.close();

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + ".zip\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @GetMapping("/listFiles/{userId}/{currentFolder}")
    public ResponseEntity<List<File>> listFiles(@PathVariable Integer userId, @PathVariable String currentFolder) {
        Folder parentFolder = folderService.findFolderByName(currentFolder);
        List<File> files;
        if (parentFolder != null) {
            files = fileService.getFilesByFolderId(parentFolder.getFolderId());
        } else {
            files = fileService.getFilesByUserId(userId);
        }
        return ResponseEntity.ok(files);
    }
}