package com.sparta.rollingpaper.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentListResponseDto {
    private List<CommentReponseDto> comments;

    public CommentListResponseDto(List<CommentReponseDto> comments) {
        this.comments = comments;
    }
}
