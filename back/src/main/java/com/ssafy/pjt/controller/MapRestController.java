package com.ssafy.pjt.controller;

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
            logger.debug("각설이 올해도 안죽고 왔다..");
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
}
