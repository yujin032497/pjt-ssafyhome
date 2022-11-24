package com.ssafy.pjt.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Qna { // 1:1문의하기

	private int qnaNo;
	private String subject;
	private String content;
	private String userId;
	private String state;
	private String contentA;
	private String registerTime;
	
}
