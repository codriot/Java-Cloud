package com.MustCloud.Cloud.Controller;

import com.MustCloud.Cloud.Users.User;
import com.MustCloud.Cloud.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.MustCloud.Cloud.Files.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserWebController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listUsers(@RequestParam String email, @RequestParam String password, Model model) {
        User user = userService.findUserByEmail(email);
        if (user != null && user.getPassword().equals(password) && "Admin".equals(user.getAccountType())) {
            model.addAttribute("userId", user.getUserId());
            model.addAttribute("users", userService.findAllUsers());
            model.addAttribute("newUser", new User());
            return "users";
        }
        return "redirect:/login";
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }
}