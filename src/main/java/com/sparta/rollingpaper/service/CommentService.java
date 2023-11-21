package com.sparta.rollingpaper.service;

import com.sparta.rollingpaper.dto.CommentListResponseDto;
import com.sparta.rollingpaper.dto.CommentReponseDto;
import com.sparta.rollingpaper.dto.CommentRequestDto;
import com.sparta.rollingpaper.entity.Comment;
import com.sparta.rollingpaper.entity.User;
import com.sparta.rollingpaper.repository.CommentRepository;
import com.sparta.rollingpaper.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentReponseDto createComment(CommentRequestDto commentRequestDto, String currentUserId) {
        // 현재 인증된 사용자 정보 조회
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 롤링페이퍼 생성
        Comment comment = new Comment(user, commentRequestDto);
        Comment savedComment = commentRepository.save(comment);

        return new CommentReponseDto(savedComment);
    }

    // 모든 게시물 조회
    public List<CommentReponseDto> getComment() {
        // 모든 게시물 조회 후 DTO로 변환하여 반환
        return commentRepository.findAll()
                .stream()
                .map(CommentReponseDto::new)
                .collect(Collectors.toList());
    }

    // 특정 사용자의 댓글만 조회
    public CommentListResponseDto getCommentsByUserId(String userId) {
        // 사용자 존재 여부 확인
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("없는 사용자입니다. ID를 다시 확인해 주세요.");
        }

        List<Comment> comments = commentRepository.findByUserUserId(userId);
        List<CommentReponseDto> commentDtos = comments.stream()
                .map(CommentReponseDto::new)
                .collect(Collectors.toList());
        return new CommentListResponseDto(commentDtos);
    }

    // comment 삭제
    @Transactional
    public void deleteComment(Long commentId, String currentUserId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 작성글이 없습니다."));

        if (!comment.getUser().getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }


//        public List<RollingPaperReponseDto> getRollingPaper() {
//            // 수정일자를 기준으로 내림차순으로 모든 게시물 조회 후 DTO로 변환하여 반환
//            return rollingPaperRepository.findAllByOrderByModifiedAtDesc()
//                    .stream()
//                    .map(RollingPaperReponseDto::new)
//                    .collect(Collectors.toList());
//        }
}
