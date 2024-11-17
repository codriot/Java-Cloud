package com.MustCloud.Cloud.Users;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private List<File> uploadedFiles;
    private List<File> downloadedFiles;





    //? Id almayan yapı örnek olarak yeni bir kullanıcı eklerken kullanılabilir.

    public User(String name, String email, String password, List<File> uploadedFiles, List<File> downloadedFiles
          ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.uploadedFiles = uploadedFiles;
        this.uploadedFiles = new ArrayList<>();
        this.downloadedFiles = new ArrayList<>();
    }

    //? Id alan yapı örnek olarak veritabanından kullanıcı bilgilerini çekerken kullanılabilir.

    public User(String id, String name, String email, String password, List<File> uploadedFiles,
            List<File> downloadedFiles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.uploadedFiles = uploadedFiles;
        this.downloadedFiles = downloadedFiles;
    }

    //? Boş constructor
    public User() {}


    //* Getter ve Setterlar
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<File> getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(List<File> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public List<File> getDownloadedFiles() {
        return downloadedFiles;
    }

    public void setDownloadedFiles(List<File> downloadedFiles) {
        this.downloadedFiles = downloadedFiles;
    }



    @Override
    public String toString() {
        return "Users [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", uploadedFiles="
                + uploadedFiles + ", downloadedFiles=" + downloadedFiles + ", lastLoginTime=" + "]";
    }

  

    // public void addUploadedFile(File file) {
    //     this.uploadedFiles.add(file);
    // }
}
