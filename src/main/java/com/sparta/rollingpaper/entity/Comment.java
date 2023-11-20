package com.sparta.rollingpaper.entity;

import com.sparta.rollingpaper.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String comment;

//    @UpdateTimestamp
//    @Column(name = "modified_at")
//    private LocalDateTime modifiedAt;

    public Comment(User user, CommentRequestDto requestDto) {
        this.user = user;
        this.comment = requestDto.getComment();
    }

}
