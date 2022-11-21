package com.ssafy.pjt.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.pjt.model.dto.Qna;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaDao {
	public int writeQna(Qna QnaDto);
	
	public List<Qna> getListQna(Map<String, Object> map);
	
	public int getTotalQna(Map<String, Object> map);
	
	public Qna getQna(int QnaNo);
	
	public void updateHit(int QnaNo);
	
	public int updateQna(Qna QnaDto);
	
	public int deleteQna(int QnaNo);

	public void deleteAll();

}
