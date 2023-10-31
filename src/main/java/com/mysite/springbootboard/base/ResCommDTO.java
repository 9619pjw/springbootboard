package com.mysite.springbootboard.base;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

// 공통 Response 객체 정의
@Data
@RequiredArgsConstructor
public class ResCommDTO implements Serializable {
	private static final long serialVersionUID = 3782250480608038900L;
	private Object data;
	private int current;
	private int pageSize;
	private int total;
	private String message;
	private String resTime;
	private String code;

	@Builder
	public ResCommDTO(Object data, int current, int pageSize, int total, String message, String resTime, String code) {
		super();
		this.data = data;
		this.current = current;
		this.pageSize = pageSize;
		this.total = total;
		this.message = message;
		this.resTime = resTime;
		this.code = code;
	}
}