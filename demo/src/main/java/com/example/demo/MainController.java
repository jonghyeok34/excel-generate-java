package com.example.demo;

import java.io.File;
import java.io.IOException;

import com.example.demo.dto.CustomerPolicyZipDto;
import com.example.demo.services.DownloadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private DownloadService service;
        
    @GetMapping("/download")
    public ResponseEntity<CustomerPolicyZipDto> download() throws IOException{
        File file = service.downloadFile();
        return ResponseEntity.ok(new CustomerPolicyZipDto(file));
    }
}
