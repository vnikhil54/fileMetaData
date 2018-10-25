package com.sample.fileMetaData.util;

import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class ConvertMetaDataToString {

    private static final DataFormatter CELL_DATA_FORMATTER = new DataFormatter();
    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertMetaDataToString.class);
    private static final String comma = ",";

    public static String processFileExcelSheet(MultipartFile uploadedFile) throws IOException {
        String fileMetaData = new String();
        if (uploadedFile == null) {
            return org.apache.commons.lang3.StringUtils.EMPTY;
        }
        XSSFWorkbook workbook = new XSSFWorkbook(uploadedFile.getInputStream());
        for (int sheetCount = 0; sheetCount < workbook.getNumberOfSheets(); sheetCount++) {
            XSSFSheet sheet = workbook.getSheetAt(sheetCount);
            String sheetMetaData = getFileMetaData(sheet);
            if (!StringUtils.isEmpty(sheetMetaData)) {
                fileMetaData = sheetMetaData;
            }
        }
        workbook.close();
        return fileMetaData;
    }

    public static String getFileMetaData(XSSFSheet sheet) {
        String sheetMetaData = new String();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                LOGGER.info("Row Number {}", row.getRowNum());
                if (cell != null) {
                    String cellValue = CELL_DATA_FORMATTER.formatCellValue(cell).trim();
                    if (!StringUtils.isEmpty(cellValue)) {
                        sheetMetaData = sheetMetaData + cellValue + comma;
                    }
                }
            }
        }
        return sheetMetaData;
    }
}
