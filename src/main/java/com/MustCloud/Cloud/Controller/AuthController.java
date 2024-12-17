package com.MustCloud.Cloud.Controller;

import com.MustCloud.Cloud.Files.FileService;
import com.MustCloud.Cloud.Users.User;
import com.MustCloud.Cloud.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;



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
            model.addAttribute("files", fileService.findFilesByUserId(user.getUserId()));
            return "dashboard";
        }
        else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        return "redirect:/users";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, @RequestParam Integer userId) {
        model.addAttribute("files", fileService.findFilesByUserId(userId));
        return "dashboard";
    }

}