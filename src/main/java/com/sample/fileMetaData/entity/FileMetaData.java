package com.sample.fileMetaData.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import java.util.Date;

@Entity
@Table(name = "file_meta_data")
public class FileMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long fileId;

    @Column(name = "file_meta_data", columnDefinition = "longtext")
    private String fileMetaData;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "email_flag")
    private boolean emailFlag = Boolean.FALSE;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean isEmailFlag() {
        return emailFlag;
    }

    public void setEmailFlag(boolean emailFlag) {
        this.emailFlag = emailFlag;
    }
}
