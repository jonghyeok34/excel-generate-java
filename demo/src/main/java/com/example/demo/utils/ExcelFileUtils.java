package com.example.demo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.demo.dto.ExcelHeaderDto;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class ExcelFileUtils {
    // 파일 임시 저장공간
    private String tempFilePath = "D:\\uploadFile";


    public File makeFile(final String fileName, ExcelHeaderDto[] headers, List<Map<String, Object>> mapList) throws IOException {
        File workDir = createWorkDir();
        File file = new File(workDir.getPath() + fileName);
        log.info("파일 저장 경로: {}", file.getPath());

        try (FileOutputStream outputStream = new FileOutputStream(file); XSSFWorkbook xssfWorkbook = new XSSFWorkbook()) {
            // 시트 생성
            XSSFSheet xssfSheet = xssfWorkbook.createSheet("결과 파일");
            // 헤더 생성
            createExcelHeader(xssfSheet.createRow(0), headers);

            // 엑셀 저장
            createExcelBody(xssfWorkbook, xssfSheet, headers, mapList);
            autoColumnWidth(xssfSheet);

            xssfWorkbook.write(outputStream);
            return file;
        }
    }

    private void createExcelHeader(XSSFRow row, ExcelHeaderDto[] headers) {
        for (ExcelHeaderDto header : headers)
            row.createCell(header.getRank()).setCellValue(header.getLabel());
    }

    private void createExcelBody(XSSFWorkbook wb, XSSFSheet xssfSheet,  ExcelHeaderDto[] headers, List<Map<String, Object>> mapList) {
        Map<String, ExcelHeaderDto> headerByName = new HashMap<>();
        for (ExcelHeaderDto header : headers) {
            headerByName.put(header.getOriginalName(), header);
        }
        
        
        for (int i = 0; i < mapList.size(); i++) {
            XSSFRow xssfRow = xssfSheet.createRow(i + 1);
            Map<String, Object> dataMap = mapList.get(i);

            dataMap.entrySet().stream().forEach(entry -> {
                String key = entry.getKey();
                Object value = entry.getValue();
                ExcelHeaderDto currentHeader = headerByName.get(key);
                int rank = currentHeader.getRank();
                if (value instanceof Integer) {
                    xssfRow.createCell(rank).setCellValue((Integer) value);
                }
                else if (value instanceof Double) {
                    xssfRow.createCell(rank).setCellValue((Double) value);
                }
                else if (value instanceof String) {
                    xssfRow.createCell(rank).setCellValue((String) value);
                }
                else if (value instanceof LocalDate) {
                    xssfRow.createCell(rank).setCellValue((LocalDate) value);
                }
                else if (value instanceof LocalDateTime) {
                    XSSFCell cell = xssfRow.createCell(rank);
                    cell.setCellValue((LocalDateTime) value);
                    cell.setCellStyle(getDateCellStyle(wb));
                } else if (value instanceof Date) {
                    xssfRow.createCell(rank).setCellValue((Date) value);
                }
                else if (value instanceof Boolean) {
                    xssfRow.createCell(rank).setCellValue((Boolean) value);
                }

            });

        }
    }

    private void autoColumnWidth(XSSFSheet xssfSheet) {
        // 컬럼 width 지정
        for (int i = 0; i < 3; i++) {
            xssfSheet.autoSizeColumn(i);
            xssfSheet.setColumnWidth(i, xssfSheet.getColumnWidth(i) + 300);
        }
    }

    // 저장할 디렉토리 만들기
    private File createWorkDir() {
        File workDir = new File(StringUtils.cleanPath(tempFilePath), UUID.randomUUID().toString());
        log.info("작업 디렉토리 생성, 경로: {}, 생성여부: {}", workDir.getPath(), workDir.mkdir());
        return workDir;
    }

    private CellStyle getDateCellStyle(XSSFWorkbook wb){
        CellStyle cellStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellStyle.setDataFormat(
        createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        return cellStyle;
    }

}
