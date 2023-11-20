package com.sparta.rollingpaper.service;

import com.sparta.rollingpaper.dto.RollingPaperReponseDto;
import com.sparta.rollingpaper.dto.RollingPaperRequestDto;
import com.sparta.rollingpaper.entity.RollingPaper;
import com.sparta.rollingpaper.entity.User;
import com.sparta.rollingpaper.repository.RollingPaperRepository;
import com.sparta.rollingpaper.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RollingPaperService {
    private final RollingPaperRepository rollingPaperRepository;
    private final UserRepository userRepository;

    @Transactional
    public RollingPaperReponseDto createRollingPaper(String userId,RollingPaperRequestDto rollingPaperRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 롤링페이퍼 생성
        RollingPaper rollingPaper = new RollingPaper(user, rollingPaperRequestDto);
        RollingPaper savedRollingPaper = rollingPaperRepository.save(rollingPaper);


        return new RollingPaperReponseDto(savedRollingPaper);
    }


    // 모든 게시물 조회
        public List<RollingPaperReponseDto> getRollingPaper() {
            // 수정일자를 기준으로 내림차순으로 모든 게시물 조회 후 DTO로 변환하여 반환
            return rollingPaperRepository.findAllByOrderByModifiedAtDesc()
                    .stream()
                    .map(RollingPaperReponseDto::new)
                    .collect(Collectors.toList());
        }

    // CRUD methods here
}

//작성시에 예외처리 : 롤링페이퍼 작성할때 로그인안된 상태면 에러 >> 로그인이 필요합니다
