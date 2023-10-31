package com.mysite.springbootboard.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {
    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max=200)
    private String subject;

    @NotEmpty(message="내용은 필수 항목입니다.")
    private String content;

    // 공지글 설정
    // TODO : 관리자 권한일 때만 공지글 작성 가능하게 변경
    private Boolean isNotice;

    public Boolean getIsNotice(){
        return isNotice;
    }

    public void setIsNotice(Boolean isNotice){
        this.isNotice = isNotice;
    }

    private String name;
}
