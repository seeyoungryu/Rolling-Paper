package com.sparta.rollingpaper.dto;

import com.sparta.rollingpaper.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentReponseDto {
    private final String comment;
//    private LocalDateTime modifiedAt;

    public CommentReponseDto(Comment comment) {
        this.comment = comment.getComment();
//        this.modifiedAt = rollingPaper.getModifiedAt();
    }
}
