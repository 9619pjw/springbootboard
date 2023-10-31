// 질문 엔티티 (Question.java) => 게시물 엔티티(Post.java)

package com.mysite.springbootboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@Entity
public class Post {

    // 게시글 고유 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 게시글 제목
    @Column(length = 200)
    private String subject;

    // 공지 게시물
    // TODO: ADMIN일떄만 활성화
    @Column
    private Boolean isNotice;

    // 게시글 내용
    @Column(columnDefinition = "TEXT")
    private String content;

    // 게시글 작성일시
    private LocalDateTime createDate;

    // 게시글이 삭제되면 그에 달린 댓글들도 모두 삭제
    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    // 글쓴이 항목
    @ManyToOne
    private SiteUser author;

    // 수정 일시
    private LocalDateTime modifyDate;

    // 게시글 좋아요
    @ManyToMany
    Set<SiteUser> voter;

   /*
   // 투표한 사용자의 수
    @Transient  // 이 어노테이션은 JPA가 해당 필드를 데이터베이스에 매핑하지 않도록 지시합니다.
    private int voterSize;

    public int getVoterSize() {
        return this.voter.size();
    }*/
}
