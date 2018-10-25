package com.sample.fileMetaData.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.fileMetaData.entity.FileMetaData;
import com.sample.fileMetaData.model.UploadFileResponseModel;
import com.sample.fileMetaData.repo.FileMetaDataRepository;

@Service
public class FileMetaDataService {

    @Autowired
    private FileMetaDataRepository fileMetaDataRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMetaDataService.class);

    public UploadFileResponseModel createFileMetaData(String userName, String metaData, String fileName) {
        UploadFileResponseModel uploadFileResponseModel = new UploadFileResponseModel();
        FileMetaData fileMetaData = new FileMetaData();
        fileMetaData.setCreatedBy(userName);
        fileMetaData.setCreatedAt(new Date());
        fileMetaData.setFileMetaData(metaData);
        fileMetaData.setFileName(UUID.randomUUID() + fileName);
        FileMetaData savedFileMetaData = fileMetaDataRepository.save(fileMetaData);
        if(savedFileMetaData != null){
            LOGGER.info("A file entity #{} is added by {} with name {}", savedFileMetaData.getFileId(), savedFileMetaData.getCreatedBy(), savedFileMetaData.getFileName());
            BeanUtils.copyProperties(savedFileMetaData, uploadFileResponseModel);
            return uploadFileResponseModel;
        }
        return null;
    }

    public UploadFileResponseModel getDeatilsById(Long fileId) throws Exception {
        UploadFileResponseModel uploadFileResponseModel = new UploadFileResponseModel();
        if (fileId != null) {
            FileMetaData fileMetaData = fileMetaDataRepository.getOne(fileId);
            if (fileMetaData == null) {
                throw new Exception(String.format("No File Meta Data found with id %d", fileId));
            } else { // If Id is not provided, we will create a new Raw Item Data
                BeanUtils.copyProperties(fileMetaData, uploadFileResponseModel);
            }
        }
        return uploadFileResponseModel;
    }

    public List<FileMetaData> getDeatilsByCreatedUser(String createdUser) {
        if(StringUtils.isNotEmpty(createdUser)) {
            return fileMetaDataRepository.findByCreatedBy(createdUser);
        }
        return null;
    }

    public List<FileMetaData> findAllByEmailFlagIsFalse() {
            return fileMetaDataRepository.findAllByEmailFlagIsFalse();
    }

    public List<FileMetaData> saveAllFileMetaDataList(List<FileMetaData> fileMetaDataList) {
        return fileMetaDataRepository.saveAll(fileMetaDataList);
    }
}
