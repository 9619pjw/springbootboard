package com.mysite.springbootboard.service;

import com.mysite.springbootboard.exception.DataNotFoundException;
import com.mysite.springbootboard.domain.Comment;
import com.mysite.springbootboard.domain.Post;
import com.mysite.springbootboard.domain.SiteUser;
import com.mysite.springbootboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 생성을 위한 메서드
    // <- 입력받은 post와 content를 사용해 Comment 객체를 생성, 저장함
    // SiteUser author : 답변 저장 시 작성자 정보도 저장
    public Comment create(Post post, String content, SiteUser author){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setPost(post);
        comment.setAuthor(author);
        this.commentRepository.save(comment);
        return comment;
    }

    // 댓글 조회
    public Comment getComment(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if(comment.isPresent()){
            return comment.get();
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

    // 댓글 수정
    public void modify(Comment comment, String content) {
        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    // 댓글 삭제
    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }


    // 댓글 추천 저장 및 취소
    public void vote(Comment comment, SiteUser siteUser){
        if(comment.getVoter().contains(siteUser)){
            comment.getVoter().remove(siteUser);
        } else {
            comment.getVoter().add(siteUser);
        }
        this.commentRepository.save(comment);
    }
}
