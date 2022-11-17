package com.ssafy.pjt.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt.model.mapper.AptDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dto.Apt;

@Service
public class AptServiceImpl implements AptService{

	@Autowired
	AptDao dao;
	@Override
	public List<Map<String, Object>> getActualPriceApt(String yyyymm, String dongCode, String sorting)
			throws SQLException {
		String year = yyyymm.substring(0, 4);
		String month = yyyymm.substring(4, 6);

		int temp = Integer.parseInt(month);
		month = String.valueOf(temp);

		List<Map<String, Object>> res = null;
		res = dao.selectApt(year, month, dongCode);

		switch(sorting) {
			case "price":
				System.out.println("price");
				res = insertionSortPrice(res);
				break;
			case "date":
				res = insertionSortDate(res);
				break;
		}


		return res;
	}

	// 삽입정렬 구현부
	public List<Map<String, Object>> insertionSortPrice(List<Map<String, Object>> res) {
		int size = res.size();
		for(int i = 1; i < size; i++) {
			Map<String, Object> map = res.get(i);
			int targetPrice = Integer.parseInt(map.get("거래금액").toString().replace(",", ""));
			int j = i -1;

			while(j >= 0 && targetPrice < Integer.parseInt(res.get(j).get("거래금액").toString().replace(",", ""))) {
				res.set(j+1, res.get(j));
				j--;
			}

			res.set(j+1, map);
		}

		return res;
	}

	// 삽입정렬 구현부
	public List<Map<String, Object>> insertionSortDate(List<Map<String, Object>> res) {
		int size = res.size();
		for(int i = 1; i < size; i++) {
			Map<String, Object> map = res.get(i);
			int targetDay = Integer.parseInt(map.get("일").toString());
			int j = i -1;

			while(j >= 0 && targetDay < Integer.parseInt(res.get(j).get("일").toString())) {
				res.set(j+1, res.get(j));
				j--;
			}

			res.set(j+1, map);
		}

		return res;
	}


	@Override
	public Map<String, String> selectSidoNames() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getGuGunNames(String sidoCode) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getDongNames(String gugunCode) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getFavorites(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<Apt>> getApt(String key, String ym) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
