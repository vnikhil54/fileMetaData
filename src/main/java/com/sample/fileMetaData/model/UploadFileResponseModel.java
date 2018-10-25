package com.sample.fileMetaData.model;

public class UploadFileResponseModel {

    private String message;

    private Long fileId;

    private String fileMetaData;

    private String fileName;

    private String createdBy;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileMetaData() {
        return fileMetaData;
    }

    public void setFileMetaData(String fileMetaData) {
        this.fileMetaData = fileMetaData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
