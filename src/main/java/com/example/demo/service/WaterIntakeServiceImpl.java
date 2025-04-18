package com.example.demo.service;

import java.util.List;

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
		repository.save(review);	
		
	}
	@Override
    public List<WaterIntake> getAllWaterIntakes() {
        return repository.findAll();
    }
	
	@Override
    public List<WaterIntake> getWaterIntakesByUserId(String userId) {
        return repository.findByUserId(userId);
	}

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
	 
	    }
    @Override
    public WaterIntake findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void update(WaterIntake intake) {
        repository.save(intake);
    }

}
