package com.ssafy.pjt.model.service;

import com.ssafy.pjt.model.dto.AddressCode;
import com.ssafy.pjt.model.dto.MapItem;

import java.util.List;
import java.util.Map;

public interface MapService {

    List<MapItem> getMapList(Map<String, String> map);

    List<MapItem> getDetailList(Map<String, String> map);

    List<AddressCode> getSido();

    List<AddressCode> getGugun(Map<String, String> map);

    List<AddressCode> getDong(Map<String, String> map);


}
