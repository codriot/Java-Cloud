package com.MustCloud.Cloud.Users;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
// Kullanıcı bilgilerini oluştur
        String id = "1";
        String name = "John Doe";
        String email = "john.doe@example.com";
        String password = "securepassword";
        List<File> uploadedFiles = new ArrayList<>();
        List<File> downloadedFiles = new ArrayList<>();

        // Users sınıfından bir nesne oluştur
        User user = new User(id, name, email, password, downloadedFiles, uploadedFiles);

        public List< User> getUsers() {
            return List.of(user); 
        }
}
