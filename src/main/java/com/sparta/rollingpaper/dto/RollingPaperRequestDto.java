package com.sparta.rollingpaper.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RollingPaperRequestDto {
    private String userId;
    private String content;
}
