package com.mysite.springbootboard.repository;

import com.mysite.springbootboard.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
