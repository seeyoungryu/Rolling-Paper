package com.sparta.rollingpaper.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BioResponseDto {
    private String userId;
    private String userName;
    private String bio;

    public BioResponseDto(String userId,String userName, String bio) {
        this.userId = userId;
        this.userName = userName;
        this.bio = bio;
    }
}
