package com.MustCloud.Cloud.Controller;

import com.MustCloud.Cloud.Files.File;
import com.MustCloud.Cloud.Files.FileService;
import com.MustCloud.Cloud.Folders.Folder;
import com.MustCloud.Cloud.Folders.FolderService;
import com.MustCloud.Cloud.Users.User;
import com.MustCloud.Cloud.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private FolderService folderService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/dashboard")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        User user = userService.findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("user", user);
            model.addAttribute("userId", user.getUserId());
            model.addAttribute("files", fileService.getFilesByUserId(user.getUserId()));
            model.addAttribute("folders", folderService.getFoldersByUserId(user.getUserId()));
            return "dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, @RequestParam Integer userId) {
        model.addAttribute("files", fileService.getFilesByUserId(userId));
        model.addAttribute("folders", folderService.getFoldersByUserId(userId));
        return "dashboard";
    }

    @GetMapping("/dashboard/{folderName}")
    public String showFolderContents(@PathVariable String folderName, Model model) {
        Folder folder = folderService.findFolderByName(folderName);
        if (folder != null) {
            model.addAttribute("folder", folder);
            model.addAttribute("files", fileService.getFilesByFolderId(folder.getFolderId()));
            model.addAttribute("folders", folderService.getSubFoldersByFolderId(folder.getFolderId()));
            return "dashboard";
        } else {
            model.addAttribute("error", "Folder not found");
            return "dashboard";
        }
    }

    @GetMapping("/api/files/list/{userId}/{currentFolder}")
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

    @GetMapping("/api/folders/list/{userId}/{currentFolder}")
    public ResponseEntity<List<Folder>> listFolders(@PathVariable Integer userId, @PathVariable String currentFolder) {
        Folder parentFolder = folderService.findFolderByName(currentFolder);
        List<Folder> folders;
        if (parentFolder != null) {
            folders = folderService.getSubFoldersByFolderId(parentFolder.getFolderId());
        } else {
            folders = folderService.getFoldersByUserId(userId);
        }
        return ResponseEntity.ok(folders);
    }
}