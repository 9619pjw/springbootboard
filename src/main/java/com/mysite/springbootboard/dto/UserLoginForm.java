package com.mysite.springbootboard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginForm {

    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자 ID는 필수항목 입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목 입니다.")
    private String password;
}
