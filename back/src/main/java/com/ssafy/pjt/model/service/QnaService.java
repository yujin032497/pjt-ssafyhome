package com.ssafy.pjt.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.pjt.model.dto.Qna;

public interface QnaService {
	public int writeQna(Qna QnaDto) throws Exception;
	
	public List<Qna> getListQna(Map<String, String> map) throws Exception;
	
	public int getTotalQna(Map<String, String> map) throws Exception;
	
	public Qna getQna(int QnaNo) throws Exception;
	
	public void updateHit(int QnaNo) throws Exception;
	
	public int updateQna(Qna QnaDto) throws Exception;
	
	public int deleteQna(int QnaNo) throws Exception;

	public void deleteAll() throws Exception;
}
