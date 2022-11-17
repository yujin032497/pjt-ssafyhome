package com.ssafy.pjt.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Apt {
	private String aptName;
	private String dong;
	private String lng;
	private String lat;
	private String dongCode;
}
