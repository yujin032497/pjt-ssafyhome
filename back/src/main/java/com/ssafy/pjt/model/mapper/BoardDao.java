package com.ssafy.pjt.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.pjt.model.dto.Board;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDao {
	public int writeArticle(Board boardDto);
	
	public List<Board> getListArticle(Map<String, Object> map);
	
	public int getTotalArticle(Map<String, Object> map);
	
	public Board getArticle(int articleNo);
	
	public void updateHit(int articleNo);
	
	public int updateArticle(Board boardDto);
	
	public int deleteArticle(int articleNo);

	public void deleteAll();

}
