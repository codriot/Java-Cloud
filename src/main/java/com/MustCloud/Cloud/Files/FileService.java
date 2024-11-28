package com.MustCloud.Cloud.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public List<File> getFilesByUserId(Integer userId) {
        return fileRepository.findByUserUserId(userId);
    }
}