package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ExcelHeaderDto {
    // 순서 0부터 시작
    private Integer rank;
    // 원래 이름
    private String originalName;
    // 설명
    private String label;
}
