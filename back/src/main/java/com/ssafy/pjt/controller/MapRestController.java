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
        logger.debug("here");
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
    public ResponseEntity<List<AddressCode>> sido() throws Exception {
        logger.info("sido - 호출");
        return new ResponseEntity<List<AddressCode>>(mapService.getSido(), HttpStatus.OK);
    }

    @GetMapping("/gugun")
    public ResponseEntity<List<AddressCode>> sido(@RequestParam("sido") String sido) throws Exception {
        return new ResponseEntity<List<AddressCode>>(mapService.getGugunInSido(sido), HttpStatus.OK);
    }


    @GetMapping("/dong")
    public ResponseEntity<List<AddressCode>> dong(@RequestParam("gugun") String gugun) throws Exception {
        return new ResponseEntity<List<AddressCode>>(mapService.getDongInGugun(gugun), HttpStatus.OK);
    }
    
}
