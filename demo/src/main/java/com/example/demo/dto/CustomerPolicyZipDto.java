package com.example.demo.dto;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.io.FileUtils;

import lombok.Getter;

@Getter
public class CustomerPolicyZipDto {

    // 파일명
    @JsonProperty(value = "name")
    private final String name;

    // 파일 base64 string
    @JsonProperty(value = "content")
    private final String content;


    public CustomerPolicyZipDto(File file) throws IOException {
        this.name = file.getName();
        this.content = toBase64(file);
    }

    private String toBase64(File file) throws IOException {
        return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
    }
}
