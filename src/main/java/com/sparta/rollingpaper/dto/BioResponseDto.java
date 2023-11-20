package com.sparta.rollingpaper.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BioResponseDto {
    private String userName;
    private String bio;

    public BioResponseDto(String userName, String bio) {
        this.userName = userName;
        this.bio = bio;
    }
}
