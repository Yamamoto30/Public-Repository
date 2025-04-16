package com.example.demo.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class WaterIntake {
    private Long id;  //主キーとなるIDフィールド
	private String userId;
	private Date intakeDate;
	private Integer amount;

}
