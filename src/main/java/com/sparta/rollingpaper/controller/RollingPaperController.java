package com.sparta.rollingpaper.controller;

import com.sparta.rollingpaper.dto.RollingPaperReponseDto;
import com.sparta.rollingpaper.dto.RollingPaperRequestDto;
import com.sparta.rollingpaper.service.RollingPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class RollingPaperController {
    private final RollingPaperService rollingPaperService;


    // CRUD endpoints here

////////////검증 로직!!
    @PostMapping("/api/user/comments/{userId}")
    public RollingPaperReponseDto createRollingPaper(@RequestBody RollingPaperRequestDto rollingPaperRequestDto) {
        // postRequestDto를 사용하여 새 게시물을 생성하고 반환
        return rollingPaperService.createRollingPaper(rollingPaperRequestDto);
    }

    // 모든 게시물 조회 API
    @GetMapping("/api/rollingpapers")
    public List<RollingPaperReponseDto> getRollingpapers() {
        return rollingPaperService.getRollingPaper();
    }

}


//상태코드

