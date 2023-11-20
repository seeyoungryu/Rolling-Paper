package com.sparta.rollingpaper.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BioRequestDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String userName;
    @NotBlank
    private String bio;
}
