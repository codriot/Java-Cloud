package com.MustCloud.Cloud.Files;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> findByUserUserId(Integer userId);
    List<File> findByFolderFolderId(Integer folderId);
}