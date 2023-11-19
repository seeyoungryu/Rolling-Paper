package com.sparta.rollingpaper.entity;

import com.sparta.rollingpaper.dto.RollingPaperRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RollingPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    public RollingPaper(RollingPaperRequestDto rollingPaperRequestDto) {
        this.content = rollingPaperRequestDto.getContent();
    }

}



