package com.example.demo.services;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.dto.ExcelHeaderDto;
import com.example.demo.utils.ExcelFileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DownloadService {
    @Autowired
    private ExcelFileUtils excelFileUtils;

    public File downloadFile() throws IOException  {
        
        System.out.println("here download");
        List<Map<String,Object>> list = 
        new ArrayList<>();

        Map<String,Object> map = new HashMap<>();
        map.put("date", new Date());
        map.put("title", "여전히 아름다운지");
        map.put("singer", "토이");
        list.add(map);
        
        final String fileName = "/테스트파일명.xlsx";
        
        // ExcelHeaderDto.builder().rank(0).originalName().label("일자").build()
        // TODO: header 설정
        ExcelHeaderDto [] headers = {
            new ExcelHeaderDto(0, "date", "일자"),
            new ExcelHeaderDto(1, "title", "제목"),
            new ExcelHeaderDto(2, "singer", "가수")
        };
        return excelFileUtils.makeFile(fileName, headers, list);
    }
}
