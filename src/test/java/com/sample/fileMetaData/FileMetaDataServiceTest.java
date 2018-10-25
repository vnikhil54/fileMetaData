package com.sample.fileMetaData;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sample.fileMetaData.entity.FileMetaData;
import com.sample.fileMetaData.model.UploadFileResponseModel;
import com.sample.fileMetaData.repo.FileMetaDataRepository;
import com.sample.fileMetaData.service.FileMetaDataService;

@RunWith(SpringJUnit4ClassRunner.class)
public class FileMetaDataServiceTest {

    @Mock
    private FileMetaDataRepository fileMetaDataRepository;

    private static final String FILE_CREATED_BY = "sampleUser";

    @InjectMocks
    private FileMetaDataService fileMetaDataService;

    @Test
    public void testDeatilsById() throws Exception {
        FileMetaData fileMetaData = buildFileMetaData();
        when(fileMetaDataRepository.getOne(any())).thenReturn(fileMetaData);

        UploadFileResponseModel uploadFileResponseModel = fileMetaDataService.getDeatilsById(1L);

        assertEquals(FILE_CREATED_BY, uploadFileResponseModel.getCreatedBy());
        verify(fileMetaDataRepository, times(1)).getOne(any());
    }

    @Test(expected = Exception.class)
    public void testDeatilsByIdWithBadRequest() throws Exception {
        when(fileMetaDataRepository.getOne(any())).thenReturn(null);
        fileMetaDataService.getDeatilsById(1L);
    }

    private FileMetaData buildFileMetaData() {
        FileMetaData fileMetaData = new FileMetaData();
        fileMetaData.setCreatedAt(new Date());
        fileMetaData.setFileId(1L);
        fileMetaData.setFileName("SampleName.xlsx");
        fileMetaData.setCreatedBy(FILE_CREATED_BY);
        return fileMetaData;
    }
}
