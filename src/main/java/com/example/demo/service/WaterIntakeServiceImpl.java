package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.WaterIntake;
import com.example.demo.repository.WaterIntakeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WaterIntakeServiceImpl implements WaterIntakeService {

	private final WaterIntakeRepository repository;
	
	@Override
	public void regist(WaterIntake review) {
		
		repository.add(review);	
		

	}

}
