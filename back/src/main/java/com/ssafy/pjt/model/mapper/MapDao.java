package com.ssafy.pjt.model.mapper;

import com.ssafy.pjt.model.dto.AddressCode;
import com.ssafy.pjt.model.dto.MapItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MapDao {
    public List<MapItem> listAptMap(Map<String, String> map);

    public List<MapItem> listHouseMap(Map<String, String> map);

    public List<MapItem> detailAptMap(Map<String, String> map);

    public List<MapItem> detailHouseMap(Map<String, String> map);

    List<AddressCode> getSido();

    List<AddressCode> getGugunInSido(Map<String, String> map);

    List<AddressCode> getDongInGugun(Map<String, String> map);
}
