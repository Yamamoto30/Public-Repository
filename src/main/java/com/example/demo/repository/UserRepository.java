// com.example.demo.repository.UserRepository.java
package com.example.demo.repository;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private static Map<String, String> users = new HashMap<>();

    public static void save(String userId, String password) {
        users.put(userId, password);
    }

    public static boolean exists(String userId) {
        return users.containsKey(userId);
    }

    public static String findPassword(String userId) {
        return users.get(userId);
    }
    
    // 追加：パスワード更新
    public static boolean updateUser(String userId, String currentPassword, String newUserId, String newPassword) {
        if (!users.containsKey(userId)) return false;
        if (!users.get(userId).equals(currentPassword)) return false; // 現在のパスワード検証
        if (!userId.equals(newUserId) && users.containsKey(newUserId)) return false;
        users.remove(userId);
        users.put(newUserId, newPassword);
        return true;
    }

}

