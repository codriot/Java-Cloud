package com.MustCloud.Cloud.Controller;

import com.MustCloud.Cloud.Files.FileService;
import com.MustCloud.Cloud.Users.User;
import com.MustCloud.Cloud.Users.UserService;
import com.MustCloud.Cloud.Util.JwtUtil;

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
              String token = JwtUtil.generateToken(user.getUserId());
            model.addAttribute("user", user);
            model.addAttribute("userId", token);
            model.addAttribute("files", fileService.findFilesByUserId(user.getUserId()));
            
            if ("Admin".equals(user.getAccountType())) {
                return "redirect:/users?email=" + email + "&password=" + password;
            } else {
                return "dashboard";
            }
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("newUser", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(User user) {
        user.setAccountType("Normal");
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, @RequestParam Integer userId) {
        model.addAttribute("files", fileService.findFilesByUserId(userId));
        return "dashboard";
    }
}