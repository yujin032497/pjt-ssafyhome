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
public class User {
	private String id;
	private String password;
	private String name;
	private String address;
	private String phone;
	private Integer isAdmin;
}
