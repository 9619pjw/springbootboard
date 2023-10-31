package com.mysite.springbootboard.service;

import com.mysite.springbootboard.domain.Comment;
import com.mysite.springbootboard.domain.Post;
import com.mysite.springbootboard.domain.SiteUser;
import com.mysite.springbootboard.repository.PostRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.mysite.springbootboard.exception.DataNotFoundException;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 검색 기능 Specification
    private Specification<Post> search(String keyword){
        return new Specification<Post>() {
            private static final long serialVersionUID = 1L;

            /**
             * p  : Root, 기준을 의미하는 Post 엔티티의 객체 <- 게시글 제목, 내용을 검색을 위해 필요
             * u1 : Post 엔티티와 SiteUser 엔티티를 조인(LEFT)하여 만든 SiteUser 엔티티 객체 <- 댓글 작성자 검색 위해 필요
             * c  : Post 엔티티와 Comment 엔티티를 조인(LEFT)하여 만든 Comment 엔티티 객체 <- 댓글 내용을 검색하기 위해 필요
             * u2 : c 객체와 SiteUser 엔티티를 조인(LEFT)하여 만든 SiteUser 엔티티 객체  <- 댓글 작성자를 검색하기 위해 필요
             */

            @Override
            public Predicate toPredicate(Root<Post> p, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true); // 중복 제거
                Join<Post, SiteUser> u1 = p.join("author", JoinType.LEFT);
                Join<Post, Comment> c = p.join("commentList", JoinType.LEFT);
                Join<Comment, SiteUser> u2 = c.join("author", JoinType.LEFT);

                return cb.or(cb.like(p.get("subject"), "%" + keyword + "%"), // 제목
                        cb.like(p.get("content"), "%" + keyword + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + keyword + "%"),    // 게시글 작성자
                        cb.like(c.get("content"), "%" + keyword + "%"),      // 댓글 내용
                        cb.like(u2.get("username"), "%" + keyword + "%"));   // 댓글 작성자
            }
        };
    }


    // 게시글 목록
    public Page<Post> getList(int page, String keyword) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        return this.postRepository.findAllByKeyword(keyword, pageable);
    }

    // 게시글 가져오기
    public Post getPost(Integer id){
        Optional<Post> post = this.postRepository.findById(id);
        if(post.isPresent()){
            return post.get();
        } else{
            throw new DataNotFoundException("post is not found");
        }
    }

    // 페이징
    public Page<Post> getList(int page) {
        // 역순 조회로 가장 최근에 작성한 게시물이 최상단에 조회
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.postRepository.findAll(pageable);
    }

    public Post create(String subject, String content, SiteUser user, Boolean isNotice) {
        Post p = new Post();
        p.setSubject(subject);
        p.setContent(content);
        p.setCreateDate(LocalDateTime.now());
        p.setAuthor(user);
        p.setIsNotice(isNotice); // 공지사항 여부 설정
        return this.postRepository.save(p); // 저장된 Post 객체 반환
    }



    // 게시글 수정
    public void modify(Post post, String subject, String content){
        post.setSubject(subject);
        post.setContent(content);
        post.setModifyDate(LocalDateTime.now());
        this.postRepository.save(post);
    }

    // 게시글 삭제
    public void delete(Post post){
        this.postRepository.delete(post);
    }


    // 게시글 추천 저장 및 취소
    public void vote(Post post, SiteUser siteUser){
        if(post.getVoter().contains(siteUser)){
            post.getVoter().remove(siteUser);
        } else {
            post.getVoter().add(siteUser);
        }
        this.postRepository.save(post);
    }


    // 공지글 조회 메소드
    public List<Post> getNotices(){
        return this.postRepository.findByIsNoticeTrueOrderByCreateDateDesc();
    }

   /* // 추천 게시글 메소드
    public void updateRecommendedPosts(){
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        List<Post> recommendedPosts = postRepository.findTop5ByCreateDateAfterOrderByVoterSizeDesc(oneWeekAgo);
    }*/

}
