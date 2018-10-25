package com.sample.fileMetaData.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.fileMetaData.entity.FileMetaData;

public interface FileMetaDataRepository extends JpaRepository<FileMetaData, Long> {

    List<FileMetaData> findByCreatedBy(String createdBy);

    List<FileMetaData> findAllByEmailFlagIsFalse();
}