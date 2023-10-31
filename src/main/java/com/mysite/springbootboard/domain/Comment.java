package com.mysite.springbootboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // 글쓴이 항목
    @ManyToOne
    private SiteUser author;

    // 수정 일시
    private LocalDateTime modifyDate;

    // 댓글 추천
    @ManyToMany
    Set<SiteUser> voter;
}
