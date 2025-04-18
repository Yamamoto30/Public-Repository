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
}
