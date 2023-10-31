package com.mysite.springbootboard.repository;

import com.mysite.springbootboard.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// DB와 연동하는 JPA 리포지터리
public interface PostRepository extends JpaRepository<Post, Integer> {

    // 글 제목으로 테이블 데이터 조회
    Post findBySubject(String subject);

    // 글 제목과 내용으로 테이블 데이터 조회
    Post findBySubjectAndContent(String subject, String content);

    // 제목에 특정 문자열이 포함된 데이터 조회
    List<Post> findBySubjectLike(String subject);

    // 페이징 구현
    Page<Post> findAll(Pageable pageable);

    // 검색 (Specification) 사용
    Page<Post> findAll(Specification<Post> spec, Pageable pageable);

    // 쿼리를 직접 작성하여 수행
    @Query("select "
            + "distinct p "
            + "from Post p "
            + "left outer join SiteUser u1 on p.author=u1 "
            + "left outer join Comment c on c.post=p "
            + "left outer join SiteUser u2 on c.author=u2 "
            + "where "
            + " p.subject like %:keyword% "
            + " or p.content like %:keyword% "
            + " or u1.username like %:keyword% "
            + " or c.content like %:keyword% "
            + " or u2.username like %:keyword% ")
    Page<Post> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);


    // 공지글 조회
    List<Post> findByIsNoticeTrueOrderByCreateDateDesc();


    /*// 게시물 추천 ... 일주일 간 추천을 가장 많이 받은 게시물 5개를 조회함
    List<Post> findTop5ByCreateDateAfterOrderByVoterSizeDesc(LocalDateTime oneWeekAgo);
    */
}
