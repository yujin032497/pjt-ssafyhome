package com.ssafy.pjt.model.service;

import com.ssafy.pjt.model.dto.AddressCode;
import com.ssafy.pjt.model.dto.MapItem;
import com.ssafy.pjt.model.mapper.MapDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MapServiceImpl implements MapService {

    @Autowired
    MapDao mapDao;

    @Override
    public List<MapItem> getMapList(java.util.Map<String, String> map) {
        List<MapItem> mapItems = new ArrayList<>();
        if (map.get("gubn").equals("apt")) {
            mapItems = mapDao.listAptMap(map);
        } else if (map.get("gubn").equals("house")) {
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

    @Override
    public List<MapItem> getDetailList(Map<String, String> map) {
        List<MapItem> mapItems = new ArrayList<>();
        if (map.get("gubn").equals("apt")) {
            mapItems = mapDao.detailAptMap(map);
        } else if (map.get("gubn").equals("house")) {
            mapItems = mapDao.detailHouseMap(map);
        } else {
            mapItems = null;
        }

        if (mapItems != null && mapItems.size() > 0) {
            for (MapItem mapItem : mapItems) {
                String ymd = mapItem.getDealYear() + mapItem.getDealMonth() + mapItem.getDealDay();
                mapItem.setDealYmd(ymd);
            }
        }
        return mapItems;
    }

    @Override
    public List<AddressCode> getSido() {
        return mapDao.getSido();
    }

    @Override
    public List<AddressCode> getGugun(Map<String, String> map) {
        return mapDao.getGugunInSido(map);
    }

    @Override
    public List<AddressCode> getDong(Map<String, String> map) {
        return mapDao.getDongInGugun(map);
    }
}
