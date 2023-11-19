package com.sparta.rollingpaper.repository;

import com.sparta.rollingpaper.entity.RollingPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 스프링 빈으로 등록되는 리포지토리 클래스를 나타내는 어노테이션
public interface RollingPaperRepository extends JpaRepository<RollingPaper, Long> {
    List<RollingPaper> findAllByOrderByModifiedAtDesc();
}
