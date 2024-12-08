package com.MustCloud.Cloud.Folders;

import com.MustCloud.Cloud.Users.User;
import com.MustCloud.Cloud.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/folders")
public class FolderController {

    @Autowired
    private FolderService folderService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Folder> createFolder(@RequestBody Folder folder, @RequestParam Integer userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        folder.setUser(user);
        Folder createdFolder = folderService.createFolder(folder);
        return ResponseEntity.ok(createdFolder);
    }

    @DeleteMapping("/delete/{folderId}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Integer folderId) {
        folderService.deleteFolder(folderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/download/{folderId}")
    public CompletableFuture<ResponseEntity<Resource>> downloadFolder(@PathVariable Integer folderId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Folder folder = folderService.findFolderById(folderId);
                if (folder == null) {
                    throw new IllegalArgumentException("Folder not found");
                }

                Path folderPath = Paths.get("uploads/" + folder.getFolderName());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

                Files.walk(folderPath)
                        .filter(path -> !Files.isDirectory(path))
                        .forEach(path -> {
                            ZipEntry zipEntry = new ZipEntry(folderPath.relativize(path).toString());
                            try {
                                zipOutputStream.putNextEntry(zipEntry);
                                Files.copy(path, zipOutputStream);
                                zipOutputStream.closeEntry();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                zipOutputStream.close();

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + folder.getFolderName() + ".zip\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<Folder>> listFolders(@PathVariable Integer userId) {
        List<Folder> folders = folderService.getFoldersByUserId(userId);
        return ResponseEntity.ok(folders);
    }
}