package com.ssafy.pjt.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MapItem {
    private String sidoName;
    private String gugunName;
    private String dongName;
    private String jibun;
    private String fullAddress;

    private String aptCode;
    private String aptName;
    private String buildYear;

    private double lat;
    private double lng;

    private String dealYear;
    private String dealMonth;
    private String dealDay;
    private String dealYmd;

    private double area;
    private int floor;
    private int type;
    private int dealAmount;
}
