package com.weather.weatherapp.service.impl;

import com.weather.weatherapp.model.User;
import com.weather.weatherapp.model.WeatherAlert;
import com.weather.weatherapp.repository.UserRepository;
import com.weather.weatherapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User addFavoriteLocation(String userId, String city) {
        userRepository.addCity(userId, city);
        System.out.println("为用户添加收藏城市: " + city);
        return getUser(userId);
    }

    @Override
    public User removeFavoriteLocation(String userId, String city) {
        userRepository.removeCity(userId, city);
        System.out.println("为用户移除收藏城市: " + city);
        return getUser(userId);
    }

    @Override
    public List<String> getFavoriteLocations(String userId) {
        return userRepository.findCitiesByUserId(userId);
    }

    @Override
    public User addWeatherAlert(String userId, WeatherAlert alert) {
        userRepository.addAlert(userId, alert);
        System.out.println("为用户添加天气提醒: " + alert.getCity());
        return getUser(userId);
    }

    @Override
    public User removeWeatherAlert(String userId, String alertId) {
        userRepository.removeAlert(userId, alertId);
        System.out.println("为用户移除天气提醒: " + alertId);
        return getUser(userId);
    }

    @Override
    public List<WeatherAlert> getWeatherAlerts(String userId) {
        return userRepository.findAlertsByUserId(userId);
    }

    @Override
    public User updateUserProfile(String userId, String email) {
        User user = getUser(userId);
        if (user != null) {
            user.setEmail(email);
            userRepository.save(user);
            System.out.println("用户信息已更新: " + email);
        }
        return user;
    }

    // 为了兼容 UserController 中的方法调用，添加这些方法
    public void addUserCity(String userId, String city) {
        addFavoriteLocation(userId, city);
    }

    public void removeUserCity(String userId, String city) {
        removeFavoriteLocation(userId, city);
    }

    public List<String> getUserCities(String userId) {
        return getFavoriteLocations(userId);
    }
}