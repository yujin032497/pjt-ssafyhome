package com.ssafy.pjt.model.service;

import com.ssafy.pjt.model.dto.MapItem;
import com.ssafy.pjt.model.mapper.MapDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapServiceImpl implements MapService {

    @Autowired
    MapDao mapDao;

    @Override
    public List<MapItem> getMapList(java.util.Map<String, String> map) {
        List<MapItem> mapItems = new ArrayList<>();
        if (map.get("type").equals("apt")) {
            map.put("type", "1");
            mapItems = mapDao.listAptMap(map);
        } else if (map.get("type").equals("house")) {
            map.put("type", "0");
            mapItems = mapDao.listHouseMap(map);
        } else {
            mapItems = null;
        }

        if (mapItems != null && mapItems.size() > 0) {
            for (MapItem mapItem : mapItems) {
                String fullAddress = mapItem.getSidoName() + " " + mapItem.getGugunName() + " " + mapItem.getDongName() + " " + mapItem.getJibun();
                mapItem.setFullAddress(fullAddress);

                String ymd = mapItem.getDealYear() + mapItem.getDealMonth() + mapItem.getDealDay();
                mapItem.setDealYmd(ymd);
            }
        }
        return mapItems;
    }
}
