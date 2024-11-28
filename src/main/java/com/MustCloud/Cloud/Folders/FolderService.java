package com.MustCloud.Cloud.Folders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;

    public List<Folder> getFoldersByUserId(Integer userId) {
        return folderRepository.findByUserUserId(userId);
    }

    public Folder createFolder(Folder folder) {
        return folderRepository.save(folder);
    }
}