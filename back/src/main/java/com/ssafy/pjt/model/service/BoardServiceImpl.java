package com.ssafy.pjt.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dto.Board;
import com.ssafy.pjt.model.mapper.BoardDao;
import com.ssafy.pjt.util.SizeConstant;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public int writeArticle(Board boardDto) throws Exception {
		System.out.println("writeArticle");
		return boardDao.writeArticle(boardDto);
	}

	@Override
	public List<Board> getListArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key))
			key = "b.user_id";
		int spp = Integer.parseInt(map.get("spp"));
		int pgno = (Integer.parseInt(map.get("pgno"))-1)*spp;
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("value") == null ? "" : map.get("value"));
		param.put("pgno", pgno);
		param.put("spp", spp);
		
		//int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		//int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		//param.put("start", start);
		//param.put("listsize", SizeConstant.LIST_SIZE);

		System.out.println(param.toString());
		return boardDao.getListArticle(param);
	}

	@Override
	public int getTotalArticle() throws Exception {
		return boardDao.getTotalArticle();
	}

	@Override
	public Board getArticle(int articleNo) throws Exception {
		return boardDao.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		boardDao.updateHit(articleNo);
		
	}

	@Override
	public int updateArticle(Board boardDto) throws Exception {
		return boardDao.updateArticle(boardDto);
	}

	@Override
	public int deleteArticle(int articleNo) throws Exception {
		return boardDao.deleteArticle(articleNo);
	}

	@Override
	public void deleteAll() throws Exception {
		boardDao.deleteAll();
		
	}

}
