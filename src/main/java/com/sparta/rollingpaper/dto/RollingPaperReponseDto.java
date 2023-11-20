package com.sparta.rollingpaper.dto;

import com.sparta.rollingpaper.entity.RollingPaper;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RollingPaperReponseDto {
    private final String content;
//    private LocalDateTime modifiedAt;

    public RollingPaperReponseDto(RollingPaper rollingPaper) {
        this.content = rollingPaper.getContent();
//        this.modifiedAt = rollingPaper.getModifiedAt();
    }
}
