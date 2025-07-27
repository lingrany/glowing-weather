package com.weather.weatherapp.repository;

import com.weather.weatherapp.model.User;
import com.weather.weatherapp.model.WeatherAlert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * 用户数据的内存实现
 */
@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> userStore = new ConcurrentHashMap<>();

    public InMemoryUserRepository() {
        // 初始化一个默认用户用于演示
        User defaultUser = new User();
        defaultUser.setUserId("user123");
        defaultUser.setUsername("默认用户");
        defaultUser.setEmail("user@example.com");
        defaultUser.setCities(new ArrayList<>());
        defaultUser.getCities().add("北京");
        defaultUser.getCities().add("上海");
        userStore.put(defaultUser.getUserId(), defaultUser);
    }

    @Override
    public User findById(String userId) {
        return userStore.get(userId);
    }

    @Override
    public User save(User user) {
        userStore.put(user.getUserId(), user);
        return user;
    }

    @Override
    public void addCity(String userId, String city) {
        User user = findById(userId);
        if (user != null && !user.getCities().contains(city)) {
            user.getCities().add(city);
            save(user);
        }
    }

    @Override
    public void removeCity(String userId, String city) {
        User user = findById(userId);
        if (user != null) {
            user.getCities().remove(city);
            save(user);
        }
    }

    @Override
    public List<String> findCitiesByUserId(String userId) {
        User user = findById(userId);
        return (user != null) ? user.getCities() : new ArrayList<>();
    }

    @Override
    public void addAlert(String userId, WeatherAlert alert) {
        User user = findById(userId);
        if (user != null) {
            user.getAlerts().add(alert);
            save(user);
        }
    }

    @Override
    public void removeAlert(String userId, String alertId) {
        User user = findById(userId);
        if (user != null) {
            user.getAlerts().removeIf(alert -> alert.getId().equals(alertId));
            save(user);
        }
    }

    @Override
    public List<WeatherAlert> findAlertsByUserId(String userId) {
        User user = findById(userId);
        return (user != null) ? user.getAlerts() : new ArrayList<>();
    }
}