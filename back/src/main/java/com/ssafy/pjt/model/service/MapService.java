package com.ssafy.pjt.model.service;

import com.ssafy.pjt.model.dto.MapItem;

import java.util.List;
import java.util.Map;

public interface MapService {

    List<MapItem> getMapList(Map<String, String> map);
}
