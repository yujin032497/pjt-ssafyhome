package com.ssafy.pjt.model.mapper;

import com.ssafy.pjt.model.dto.MapItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapDao {
    public List<MapItem> listAptMap(java.util.Map<String, String> map);

    public List<MapItem> listHouseMap(java.util.Map<String, String> map);
}
