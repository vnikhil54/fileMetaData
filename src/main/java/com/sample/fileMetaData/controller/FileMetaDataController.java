package com.sample.fileMetaData.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import com.sample.fileMetaData.entity.FileMetaData;
import com.sample.fileMetaData.model.UploadFileResponseModel;
import com.sample.fileMetaData.service.FileMetaDataService;
import com.sample.fileMetaData.util.ConvertMetaDataToString;

@RestController
@RequestMapping(value = "/fileMetaData")
public class FileMetaDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMetaDataController.class);

    @Autowired
    private FileMetaDataService fileMetaDataService;

    @RequestMapping(value = "/upload",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody UploadFileResponseModel createFileMetaData(@RequestHeader(value = "username") String userName, @RequestPart("uploadedFile") @NotNull MultipartFile uploadedFile)
            throws Exception {
        UploadFileResponseModel uploadFileResponseModel = new UploadFileResponseModel();
        try {
            String fileName = uploadedFile.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(fileName);
            if (uploadedFile.isEmpty() && (fileExtension == "xlsx")) {
                LOGGER.info("User Should Not Upload Empty Sheet");
                uploadFileResponseModel.setMessage(userName + "You failed to upload because the file was empty.");
                return uploadFileResponseModel;
            } else {
                String metaData = ConvertMetaDataToString.processFileExcelSheet(uploadedFile);
                if (!StringUtils.isEmpty(metaData)) {
                    uploadFileResponseModel = fileMetaDataService.createFileMetaData(userName, metaData, fileName);
                    if(uploadFileResponseModel != null){
                        return uploadFileResponseModel;
                    } else {
                        uploadFileResponseModel.setMessage("Exception occured while saving the file meta data");
                    }
                } else {
                    uploadFileResponseModel.setMessage("Exception occured while reading the excel file");
                }
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occurred while uploading file", exception);
            throw new Exception("Exception occurred while uploading file" + exception);
        }
        return uploadFileResponseModel;
    }

    @GetMapping(path = "byFileId/{fileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UploadFileResponseModel getEventsByType(@PathVariable Long fileId) throws Exception {
        return fileMetaDataService.getDeatilsById(fileId);
    }

    @GetMapping(path = "/byCreatedUser/{createdUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileMetaData> getEventsByOwnerId(@PathVariable String createdUser) throws Exception {
        return fileMetaDataService.getDeatilsByCreatedUser(createdUser);
    }
}
