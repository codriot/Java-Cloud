package com.MustCloud.Cloud.Users;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        // Veritabanına kullanıcı kaydet
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    
    public User findUserByEmail(String email) {
        // Email ile kullanıcıyı bul
        return userRepository.findByEmail(email);
    }

    public User findUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }
}