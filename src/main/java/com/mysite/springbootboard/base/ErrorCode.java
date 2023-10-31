package com.mysite.springbootboard.base;

// 에러코드 정의
public interface ErrorCode {
	String name(); 
	String getErrorcode(); 
	String getGmessage();
	String getDmessage();
}
