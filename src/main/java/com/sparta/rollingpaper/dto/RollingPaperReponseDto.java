package com.sparta.rollingpaper.dto;

import com.sparta.rollingpaper.entity.RollingPaper;
import lombok.Getter;

@Getter
public class RollingPaperReponseDto {
    private final String content;

    public RollingPaperReponseDto(RollingPaper savedRollingPaper) {
        this.content = savedRollingPaper.getContent();
    }
}
