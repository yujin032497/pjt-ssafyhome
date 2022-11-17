package com.ssafy.pjt.model.mapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt.model.dto.Apt;

public interface AptDao {
	public Map<String, String> selectSidoNames() throws SQLException;
	
	public Map<String, String> selectGugunNames(String sidoCode) throws SQLException;
	
	public Map<String, String> selectDongNames(String gugunCode) throws SQLException;
	
	public Map<String, String> selectUserFavorite(String id) throws SQLException;
	
	public Map<String, List<Apt>> selectAptLocation(String key, String year, String month) throws SQLException;

	public List<Map<String, Object>> selectApt(String year, String month, String dongCode) throws SQLException;
	
	
}
