package com.ssafy.pjt.controller;

import com.ssafy.pjt.model.dto.AddressCode;
import com.ssafy.pjt.model.dto.MapItem;
import com.ssafy.pjt.model.service.MapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/map")
public class MapRestController {
    @Autowired
    MapService mapService;

    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @GetMapping
    public ResponseEntity<?> list(@RequestParam Map<String, String> map) {
        try {
            logger.debug(map.toString());
            List<MapItem> mapList = mapService.getMapList(map);

            if (mapList != null && mapList.size() > 0) {
                return new ResponseEntity<>(mapList, HttpStatus.OK);
            } else if (mapList != null) {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<?> detail(@RequestParam Map<String, String> map) {
        logger.debug(map.toString());
        try {
            List<MapItem> detailList = mapService.getDetailList(map);

            if (detailList != null && detailList.size() > 0) {
                return new ResponseEntity<>(detailList, HttpStatus.OK);
            } else if (detailList != null) {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sido")
    public ResponseEntity<?> sido() {
        try {
            logger.debug("sido called");
            List<AddressCode> sidos = mapService.getSido();
            if (sidos != null) {
                return new ResponseEntity<>(sidos, HttpStatus.OK);
            }
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/gugun")
    public ResponseEntity<?> gugun(@RequestParam Map<String, String> map) {
        try {
            logger.debug("{}\tgugun called", map.toString());
            List<AddressCode> guguns = mapService.getGugun(map);
            if (guguns != null) {
                return new ResponseEntity<>(guguns, HttpStatus.OK);
            }
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/dong")
    public ResponseEntity<?> dong(@RequestParam Map<String, String> map) {
        try {
            logger.debug("{}\tdong called", map.toString());
            List<AddressCode> dongs = mapService.getDong(map);
            if (dongs != null) {
                return new ResponseEntity<>(dongs, HttpStatus.OK);
            }
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/detail")
//    public ResponseEntity<?> detail(@RequestParam Map<String, String> map) {
//        try{
//
//        }
//    }
}
