package com.ssafy.pjt.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddressCode {
    private String sidoCode;
    private String sidoName;

    private String gugunCode;
    private String gugunName;

    private String dongCode;
    private String dongName;
}
