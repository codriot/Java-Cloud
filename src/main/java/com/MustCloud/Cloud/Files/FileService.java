package com.MustCloud.Cloud.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    public List<File> findFilesByUserId(Integer userId) {
        return fileRepository.findByUserUserId(userId);
    }

    public File findFileById(Integer fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }

    public void deleteFile(Integer fileId) {
        fileRepository.deleteById(fileId);
    }
}