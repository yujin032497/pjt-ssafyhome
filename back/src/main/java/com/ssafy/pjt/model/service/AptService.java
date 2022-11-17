package com.ssafy.pjt.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt.model.dto.Apt;

public interface AptService {
public List<Map<String, Object>> getActualPriceApt(String yyyymm, String dongCode, String sorting) throws SQLException;
	
	public Map<String, String> selectSidoNames() throws SQLException;
	
	public Map<String, String> getGuGunNames(String sidoCode) throws SQLException;
	
	public Map<String, String> getDongNames(String gugunCode) throws SQLException;
	
	public Map<String, String> getFavorites(String userId) throws SQLException;
	
	public Map<String, List<Apt>> getApt(String key, String ym) throws SQLException;
}
