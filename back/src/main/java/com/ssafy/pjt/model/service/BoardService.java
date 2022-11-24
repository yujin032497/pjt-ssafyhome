package com.ssafy.pjt.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt.model.dto.Apt;
import com.ssafy.pjt.model.dto.Board;


public interface BoardService {
	public int writeArticle(Board boardDto) throws Exception;
	
	public List<Board> getListArticle(Map<String, String> map) throws Exception;
	
	public int getTotalArticle(Map<String, String> map) throws Exception;
	
	public Board getArticle(int articleNo) throws Exception;
	
	public void updateHit(int articleNo) throws Exception;
	
	public int updateArticle(Board boardDto) throws Exception;
	
	public int deleteArticle(int articleNo) throws Exception;

	public void deleteAll() throws Exception;
	
}
