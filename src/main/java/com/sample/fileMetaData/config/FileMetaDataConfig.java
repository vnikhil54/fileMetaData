package com.sample.fileMetaData.config;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.sample.fileMetaData.controller.FileMetaDataController;
import com.sample.fileMetaData.entity.FileMetaData;
import com.sample.fileMetaData.service.FileMetaDataService;

@Configuration
@EnableScheduling
public class FileMetaDataConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMetaDataConfig.class);

    @Autowired
    private FileMetaDataService fileMetaDataService;

    @Autowired
    private JavaMailSender sender;

    @Scheduled(cron = "0 * * ? * *") // Runs Every Min
    public void scheduleTaskUsingCronExpression() throws Exception {

        long now = System.currentTimeMillis() / 1000;
        List<FileMetaData> fileMetaDataList = fileMetaDataService.findAllByEmailFlagIsFalse();
        if(CollectionUtils.isNotEmpty(fileMetaDataList)) {
            sendEmail();
        }
        fileMetaDataList.forEach(fileMetaData -> {
            fileMetaData.setEmailFlag(Boolean.TRUE);
        });
        fileMetaDataService.saveAllFileMetaDataList(fileMetaDataList);
        LOGGER.info("schedule tasks using cron jobs - " + now + " -- size : "+fileMetaDataList.size());
    }

    private void sendEmail() {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo("*****@gmail.com");
            helper.setText("DB Contains new records");
            helper.setSubject("DB Contains new records");
            sender.send(message);
        } catch(Exception e) {
            LOGGER.error("Email sent failed due to ", e);
        }

    }
}
