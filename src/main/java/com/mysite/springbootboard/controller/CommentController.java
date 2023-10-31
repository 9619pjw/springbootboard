package com.mysite.springbootboard.controller;

import com.mysite.springbootboard.base.CommonErrorCode;
import com.mysite.springbootboard.base.CommonUtil;
import com.mysite.springbootboard.base.ResCommDTO;
import com.mysite.springbootboard.domain.Comment;
import com.mysite.springbootboard.dto.CommentForm;
import com.mysite.springbootboard.service.CommentService;
import com.mysite.springbootboard.domain.Post;
import com.mysite.springbootboard.service.PostService;
import com.mysite.springbootboard.domain.SiteUser;
import com.mysite.springbootboard.service.UserService;
import lombok.RequiredArgsConstructor;

import org.hibernate.HibernateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final PostService postService;

    private final CommentService commentService;

    private final UserService userService;

    // 댓글 저장
    // 게시글의 ID 값을 받아 코멘트 생성
    @PostMapping("/create/{id}")
    @ResponseBody
    public ResCommDTO createComment(@PathVariable("id") Integer id,
                                    @RequestBody CommentForm commentForm){
        try {
            Post post = this.postService.getPost(id);
            SiteUser siteUser = this.userService.getUser(commentForm.getName());
            // 댓글을 저장한다.
            this.commentService.create(post, commentForm.getContent(), siteUser);

        // 오류 처리(데이터바인딩 오류, SQL 오류, 전체 오류(내부서버))
        } catch(DataIntegrityViolationException e) { 
            return CommonUtil.setResponseObject(null, CommonErrorCode.DATA_ALREADY_EXISTS.getErrorcode() + "", CommonErrorCode.DATA_ALREADY_EXISTS.getGmessage() + " : " + CommonErrorCode.DATA_ALREADY_EXISTS.getDmessage());
        } catch(HibernateException e) {
            return CommonUtil.setResponseObject(null, CommonErrorCode.MSSQL_SERVER_RROR.getErrorcode() + "", CommonErrorCode.MSSQL_SERVER_RROR.getGmessage() + " : " + CommonErrorCode.MSSQL_SERVER_RROR.getDmessage());
        } catch(Exception e) {
            return CommonUtil.setResponseObject(null, CommonErrorCode.INTERNAL_SERVER_ERROR.getErrorcode() + "", CommonErrorCode.INTERNAL_SERVER_ERROR.getGmessage() + " : " + CommonErrorCode.INTERNAL_SERVER_ERROR.getDmessage());
        }

        // 맵 생성
        HashMap map = new HashMap();

        // Response Object 생성
        ResCommDTO res = CommonUtil.setResponseObject(map, CommonErrorCode.SUCCESS.getErrorcode() + "", 
                                                            CommonErrorCode.SUCCESS.getGmessage() + " : " + 
                                                            CommonErrorCode.SUCCESS.getDmessage());
        return res;
    }

    // 댓글 수정 URL 처리
    @GetMapping("/modify/{id}")
    @ResponseBody
    public ResCommDTO getCommentModify(@RequestBody CommentForm commentForm, 
                                        @PathVariable("id") Integer id) {
        try {
            Comment comment = this.commentService.getComment(id);
            commentForm.setContent(comment.getContent());
        // 오류 처리(데이터바인딩 오류, SQL 오류, 전체 오류(내부서버))
        } catch(DataIntegrityViolationException e) {
            return CommonUtil.setResponseObject(null, CommonErrorCode.DATA_ALREADY_EXISTS.getErrorcode() + "", CommonErrorCode.DATA_ALREADY_EXISTS.getGmessage() + " : " + CommonErrorCode.DATA_ALREADY_EXISTS.getDmessage());
        } catch(HibernateException e) {
            return CommonUtil.setResponseObject(null, CommonErrorCode.MSSQL_SERVER_RROR.getErrorcode() + "", CommonErrorCode.MSSQL_SERVER_RROR.getGmessage() + " : " + CommonErrorCode.MSSQL_SERVER_RROR.getDmessage());
        } catch(Exception e) {
            return CommonUtil.setResponseObject(null, CommonErrorCode.INTERNAL_SERVER_ERROR.getErrorcode() + "", CommonErrorCode.INTERNAL_SERVER_ERROR.getGmessage() + " : " + CommonErrorCode.INTERNAL_SERVER_ERROR.getDmessage());
        }

        HashMap map = new HashMap();

        ResCommDTO res = CommonUtil.setResponseObject(map, CommonErrorCode.SUCCESS.getErrorcode() + "", 
                                                            CommonErrorCode.SUCCESS.getGmessage() + " : " + 
                                                            CommonErrorCode.SUCCESS.getDmessage());
        return res;
    }

    // 댓글 수정 요청 by 폼
    @PostMapping("/modify/{id}")
    @ResponseBody
    public ResCommDTO commentModify(@RequestBody CommentForm commentForm,
                                    @PathVariable("id") Integer id) {
        try {
            Comment comment = this.commentService.getComment(id);
            this.commentService.modify(comment, commentForm.getContent());
        // 오류 처리(데이터바인딩 오류, SQL 오류, 전체 오류(내부서버))
        } catch(DataIntegrityViolationException e) {
            return CommonUtil.setResponseObject(null, CommonErrorCode.DATA_ALREADY_EXISTS.getErrorcode() + "", CommonErrorCode.DATA_ALREADY_EXISTS.getGmessage() + " : " + CommonErrorCode.DATA_ALREADY_EXISTS.getDmessage());
        } catch(HibernateException e) {
            return CommonUtil.setResponseObject(null, CommonErrorCode.MSSQL_SERVER_RROR.getErrorcode() + "", CommonErrorCode.MSSQL_SERVER_RROR.getGmessage() + " : " + CommonErrorCode.MSSQL_SERVER_RROR.getDmessage());
        } catch(Exception e) {
            return CommonUtil.setResponseObject(null, CommonErrorCode.INTERNAL_SERVER_ERROR.getErrorcode() + "", CommonErrorCode.INTERNAL_SERVER_ERROR.getGmessage() + " : " + CommonErrorCode.INTERNAL_SERVER_ERROR.getDmessage());
        }

        // 맵 생성
        HashMap map = new HashMap();

        // Response Object 생성
        ResCommDTO res = CommonUtil.setResponseObject(map, CommonErrorCode.SUCCESS.getErrorcode() + "", 
                                                            CommonErrorCode.SUCCESS.getGmessage() + " : " + 
                                                            CommonErrorCode.SUCCESS.getDmessage());
        return res;
    }

    // 댓글 삭제 URL
    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResCommDTO answerDelete(@PathVariable("id") Integer id) {
        try {
            Comment comment = this.commentService.getComment(id);
            this.commentService.delete(comment);
        // 오류 처리(데이터바인딩 오류, SQL 오류, 전체 오류(내부서버))
        } catch(DataIntegrityViolationException e) {
            return CommonUtil.setResponseObject(null, CommonErrorCode.DATA_ALREADY_EXISTS.getErrorcode() + "", CommonErrorCode.DATA_ALREADY_EXISTS.getGmessage() + " : " + CommonErrorCode.DATA_ALREADY_EXISTS.getDmessage());
        } catch(HibernateException e) {
            return CommonUtil.setResponseObject(null, CommonErrorCode.MSSQL_SERVER_RROR.getErrorcode() + "", CommonErrorCode.MSSQL_SERVER_RROR.getGmessage() + " : " + CommonErrorCode.MSSQL_SERVER_RROR.getDmessage());
        } catch(Exception e) {
            return CommonUtil.setResponseObject(null, CommonErrorCode.INTERNAL_SERVER_ERROR.getErrorcode() + "", CommonErrorCode.INTERNAL_SERVER_ERROR.getGmessage() + " : " + CommonErrorCode.INTERNAL_SERVER_ERROR.getDmessage());
        }

        // 맵 생성
        HashMap map = new HashMap();

        // Response Object 생성
        ResCommDTO res = CommonUtil.setResponseObject(map, CommonErrorCode.SUCCESS.getErrorcode() + "", 
                                                            CommonErrorCode.SUCCESS.getGmessage() + " : " + 
                                                            CommonErrorCode.SUCCESS.getDmessage());
        return res;
    }

    // 댓글 추천 URL 처리
    // querystring 방식으로 name 값 추가
    @GetMapping("/vote/{id}")
    @ResponseBody
    public ResCommDTO commentVote(@RequestParam String name, 
                                @PathVariable("id") Integer id){
        try {
            Comment comment= this.commentService.getComment(id);
            SiteUser siteUser = this.userService.getUser(name);
            this.commentService.vote(comment, siteUser);

        // 오류 처리(데이터바인딩 오류, SQL 오류, 전체 오류(내부서버))
        } catch(DataIntegrityViolationException e) {
            return CommonUtil.setResponseObject(null, CommonErrorCode.DATA_ALREADY_EXISTS.getErrorcode() + "", CommonErrorCode.DATA_ALREADY_EXISTS.getGmessage() + " : " + CommonErrorCode.DATA_ALREADY_EXISTS.getDmessage());
        } catch(HibernateException e) {
            return CommonUtil.setResponseObject(null, CommonErrorCode.MSSQL_SERVER_RROR.getErrorcode() + "", CommonErrorCode.MSSQL_SERVER_RROR.getGmessage() + " : " + CommonErrorCode.MSSQL_SERVER_RROR.getDmessage());
        } catch(Exception e) {
            return CommonUtil.setResponseObject(null, CommonErrorCode.INTERNAL_SERVER_ERROR.getErrorcode() + "", CommonErrorCode.INTERNAL_SERVER_ERROR.getGmessage() + " : " + CommonErrorCode.INTERNAL_SERVER_ERROR.getDmessage());
        }

        // 맵 생성
        HashMap map = new HashMap();

        // Response Object 생성
        ResCommDTO res = CommonUtil.setResponseObject(map, CommonErrorCode.SUCCESS.getErrorcode() + "", 
                                                            CommonErrorCode.SUCCESS.getGmessage() + " : " + 
                                                            CommonErrorCode.SUCCESS.getDmessage());
        return res;
    }
}
