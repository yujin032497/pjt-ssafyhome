package com.ssafy.pjt.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dto.Qna;
import com.ssafy.pjt.model.mapper.QnaDao;

@Service
public class QnaServiceImpl implements QnaService{
	
	@Autowired
	private QnaDao QnaDao;
	
	@Override
	public int writeQna(Qna QnaDto) throws Exception {
		System.out.println("writeQna");
		return QnaDao.writeQna(QnaDto);
	}

	@Override
	public List<Qna> getListQna(Map<String, String> map) throws Exception {
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
		return QnaDao.getListQna(param);
	}

	@Override
	public int getTotalQna(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key))
			key = "b.user_id";

		param.put("key", key == null ? "" : key);
		param.put("word", map.get("value") == null ? "" : map.get("value"));
		
		System.out.println(param.toString());
		return QnaDao.getTotalQna(param);
	}

	@Override
	public Qna getQna(int QnaNo) throws Exception {
		return QnaDao.getQna(QnaNo);
	}

	@Override
	public void updateHit(int QnaNo) throws Exception {
		QnaDao.updateHit(QnaNo);
		
	}

	@Override
	public int updateQna(Qna QnaDto) throws Exception {
		return QnaDao.updateQna(QnaDto);
	}

	@Override
	public int deleteQna(int QnaNo) throws Exception {
		return QnaDao.deleteQna(QnaNo);
	}

	@Override
	public void deleteAll() throws Exception {
		QnaDao.deleteAll();
		
	}

}
