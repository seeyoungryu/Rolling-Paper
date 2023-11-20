package com.sparta.rollingpaper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.rollingpaper.dto.RollingPaperRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RollingPaper {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rollingPaperId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public RollingPaper(User user, RollingPaperRequestDto requestDto) {
        this.user = user;
        this.content = requestDto.getContent();
    }

}
