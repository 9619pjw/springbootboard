package com.mysite.springbootboard.controller;

import com.mysite.springbootboard.base.CommonErrorCode;
import com.mysite.springbootboard.base.CommonUtil;
import com.mysite.springbootboard.base.ResCommDTO;
import com.mysite.springbootboard.config.UserSecurityService;
import com.mysite.springbootboard.dto.UserCreateForm;
import com.mysite.springbootboard.dto.UserLoginForm;
import com.mysite.springbootboard.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    private final UserService userService;

    private final UserSecurityService userSecurityService;

     // 회원가입 진행
     // 로그인 성공 처리 - 프론트에서 로컬 스토리지, 쿠키로 세션 유지
    
    @CrossOrigin(originPatterns = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody UserLoginForm loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User login successfully!...", HttpStatus.OK);
    }

    // 회원가입 진행
    @PostMapping("/signup")
    @ResponseBody
    public ResCommDTO signup(@RequestBody UserCreateForm userCreateForm){
        if(!userCreateForm.getPassword1().equals((userCreateForm.getPassword2()))){
            return CommonUtil.setResponseObject(null, CommonErrorCode.INVALID_USER_HEADER.getErrorcode() + "", CommonErrorCode.INTERNAL_SERVER_ERROR.getGmessage() + " : " + CommonErrorCode.INVALID_USER_HEADER.getDmessage());
        }

        // 중복 가입 방지
        try {
            userService.create(userCreateForm.getUsername(),
            userCreateForm.getEmail(),userCreateForm.getPassword1());
        // 오류 처리(데이터바인딩 오류, SQL 오류, 전체 오류(내부서버))
        }catch(DataIntegrityViolationException e){
            e.printStackTrace();
            return CommonUtil.setResponseObject(null, CommonErrorCode.ALREADY_USED_ID.getErrorcode() + "", CommonErrorCode.INTERNAL_SERVER_ERROR.getGmessage() + " : " + CommonErrorCode.ALREADY_USED_ID.getDmessage());
        }catch(Exception e) {
            e.printStackTrace();
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
