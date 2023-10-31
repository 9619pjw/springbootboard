package com.mysite.springbootboard.controller;

import com.mysite.springbootboard.dto.CommentForm;
import com.mysite.springbootboard.base.CommonErrorCode;
import com.mysite.springbootboard.base.CommonUtil;
import com.mysite.springbootboard.base.ResCommDTO;
import com.mysite.springbootboard.domain.Post;
import com.mysite.springbootboard.dto.PostForm;
import com.mysite.springbootboard.service.PostService;
import com.mysite.springbootboard.domain.SiteUser;
import com.mysite.springbootboard.service.UserService;
import lombok.RequiredArgsConstructor;

import org.hibernate.HibernateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;

@RequestMapping("/post")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final UserService userService;

    // 게시글 목록
    @GetMapping("/list")
    @ResponseBody
    public ResCommDTO list(@RequestParam(value="page", defaultValue="0") int page,
                            @RequestParam(value="keyword", defaultValue="") String keyword){
        // Result Object 초기화(init)
        Page<Post> paging = null;
        List<Post> notices = null;

        try {
            // paging 처리 및 Result 데이터 삽입
            paging = this.postService.getList(page, keyword);
            notices = this.postService.getNotices();

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
        map.put("paging", paging);
        map.put("notices", notices);
        map.put("keyword", keyword);

        // Response Object 생성
        ResCommDTO res = CommonUtil.setResponseObject(map, CommonErrorCode.SUCCESS.getErrorcode() + "", 
                                                            CommonErrorCode.SUCCESS.getGmessage() + " : " + 
                                                            CommonErrorCode.SUCCESS.getDmessage());
        return res;
    }

    // 공지글 목록
    @GetMapping("/notices")
    @ResponseBody
    public ResCommDTO noticeList(@RequestParam(value="page", defaultValue="0") int page,
                                @RequestParam(value="keyword", defaultValue="") String keyword){
        Page<Post> paging = null;
        List<Post> notices = null;

        try {
            paging = this.postService.getList(page, keyword);
            notices = this.postService.getNotices();

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
        map.put("paging", paging);
        map.put("notices", notices);
        map.put("keyword", keyword);

        // Response Object 생성
        ResCommDTO res = CommonUtil.setResponseObject(map, CommonErrorCode.SUCCESS.getErrorcode() + "", 
                                                            CommonErrorCode.SUCCESS.getGmessage() + " : " + 
                                                            CommonErrorCode.SUCCESS.getDmessage());
        return res;
    }

    // 상세 보기
    @GetMapping(value = "/detail/{id}")
    @ResponseBody
    public ResCommDTO detail(@PathVariable("id") Integer id, CommentForm commentForm){
        Post post = null;

        try {
            post = this.postService.getPost(id);
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
        map.put("post", post);

        // Response Object 생성
        ResCommDTO res = CommonUtil.setResponseObject(map, CommonErrorCode.SUCCESS.getErrorcode() + "", 
                                                            CommonErrorCode.SUCCESS.getGmessage() + " : " + 
                                                            CommonErrorCode.SUCCESS.getDmessage());
        return res;
    }

    // 게시글 생성
    /*x
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String postCreate(PostForm postForm){
        return "post_form";
    }
    */

    @PostMapping("/create")
    @ResponseBody
    // subject, content 항목을 지닌 폼이 전송되면
    // PostForm의 subject, content 속성이 자동으로 바인딩됨.
    public ResCommDTO postCreate(@RequestBody PostForm postForm) {
        // 게시글 저장
        try {
            SiteUser siteUser = this.userService.getUser(postForm.getName());
            Boolean isNotice = postForm.getIsNotice();
            this.postService.create(postForm.getSubject(), postForm.getContent(), siteUser, isNotice);
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

    // 게시글 수정
    @GetMapping("/modify/{id}")
    @ResponseBody
    public ResCommDTO getPostModify(@RequestBody PostForm postForm, @PathVariable("id") Integer id){
        try {
            Post post = this.postService.getPost(id); 
            postForm.setSubject(post.getSubject());
            postForm.setContent(post.getContent());
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

    // 게시글 수정 내역 저장
    @PostMapping("/modify/{id}")
    @ResponseBody
    public ResCommDTO postModify(@RequestBody PostForm postForm,
                                @PathVariable("id") Integer id){
        try {
            Post post = this.postService.getPost(id);
            this.postService.modify(post, postForm.getSubject(), postForm.getContent());
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

    // 게시글 삭제 ... URL 처리
    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResCommDTO postDelete(@PathVariable("id") Integer id){
        Post post = this.postService.getPost(id);
        try {
            this.postService.delete(post);
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

    // 추천 버튼 눌렀을때 호출되는 URL 처리
    // querystring 방식으로 {URL}?name= 으로 name 값 설정, {id} id 값 추가
    @GetMapping("/vote/{id}")
    public ResCommDTO postVote(@RequestParam String name, @PathVariable("id") Integer id){
        try {
            Post post = this.postService.getPost(id);
            SiteUser siteUser = this.userService.getUser(name);
            this.postService.vote(post, siteUser);

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

        // Response Object
        ResCommDTO res = CommonUtil.setResponseObject(map, CommonErrorCode.SUCCESS.getErrorcode() + "", 
                                                            CommonErrorCode.SUCCESS.getGmessage() + " : " + 
                                                            CommonErrorCode.SUCCESS.getDmessage());
        return res;
    }
}
