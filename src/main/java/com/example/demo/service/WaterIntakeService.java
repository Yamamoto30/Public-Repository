package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.WaterIntake;

public interface WaterIntakeService {

	void regist(WaterIntake review);
	
	//全ての水分摂取データを取得
    List<WaterIntake> getAllWaterIntakes();
    List<WaterIntake> getWaterIntakesByUserId(String userId);
    
    //指定されたIDの水分摂取データを削除
    void delete(Long id);
    
 // IDを指定して1件のデータを取得
    WaterIntake findById(Long id);

    // データの更新
    void update(WaterIntake intake);

}

