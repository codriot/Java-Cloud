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

    public void deleteFolder(Integer folderId) {
        folderRepository.deleteById(folderId);
    }

    public Folder findFolderById(Integer folderId) {
        return folderRepository.findById(folderId).orElse(null);
    }

    public Folder findFolderByName(String folderName) {
        return folderRepository.findByFolderName(folderName);
    }

    public List<Folder> getSubFoldersByFolderId(int folderId) {
        Folder parentFolder = findFolderById(folderId);
        return folderRepository.findByParentFolder(parentFolder);
    }
}