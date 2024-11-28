package com.MustCloud.Cloud.Folders;

import com.MustCloud.Cloud.Users.User;
import com.MustCloud.Cloud.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/folders")
public class FolderController {

    @Autowired
    private FolderService folderService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Folder createFolder(@RequestBody Folder folder, @RequestParam Integer userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        folder.setUser(user);
        return folderService.createFolder(folder);
    }
}