package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.WaterIntake;

@Repository
public class WaterIntakeRepositoryImpl implements WaterIntakeRepository {

	@Override
	public void add(WaterIntake review) {
	
		System.out.println("---登録---");
		System.out.println(review);
		

	}

}
