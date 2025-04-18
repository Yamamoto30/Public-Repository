package com.example.demo.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.WaterIntake;

@Repository
public class WaterIntakeRepositoryImpl implements WaterIntakeRepository {
    // 仮想のデータストア（実際はデータベースを使用すべき）
    private static final Map<Long, WaterIntake> store = new HashMap<>();
    private static Long sequence = 1L;
    
    @Override
    public void save(WaterIntake waterIntake) {
        System.out.println("---登録/更新---");
        System.out.println(waterIntake);
        
        // IDが設定されていない場合は新規データとして扱う
        if (waterIntake.getId() == null) {
            waterIntake.setId(sequence++);
        }
        
        // データの保存
        store.put(waterIntake.getId(), waterIntake);
    }
    
    @Override
    public List<WaterIntake> findAll() {
        System.out.println("---全件取得---");
        return new ArrayList<>(store.values());
    }
    
    @Override
    public void deleteById(Long id) {
        System.out.println("---削除 ID:" + id + "---");
        store.remove(id);
    }
    
    @Override
    public Optional<WaterIntake> findById(Long id) {
        System.out.println("---ID検索 ID:" + id + "---");
        return Optional.ofNullable(store.get(id));
    }
    
    @Override
    public List<WaterIntake> findByUserId(String userId) {
        System.out.println("---ユーザーID検索 userId:" + userId + "---");
        
        // userId でフィルタリング
        List<WaterIntake> result = new ArrayList<>();
        for (WaterIntake intake : store.values()) {
            if (intake.getUserId().equals(userId)) {
                result.add(intake);
            }
        }
        return result;
    }

}
