package com.weather.weatherapp.service;

import com.weather.weatherapp.model.User;
import com.weather.weatherapp.model.WeatherAlert;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {
    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    User getUser(String userId);
    
    /**
     * 添加收藏城市
     * @param userId 用户ID
     * @param city 城市名称
     * @return 更新后的用户信息
     */
    User addFavoriteLocation(String userId, String city);
    
    /**
     * 删除收藏城市
     * @param userId 用户ID
     * @param city 城市名称
     * @return 更新后的用户信息
     */
    User removeFavoriteLocation(String userId, String city);
    
    /**
     * 获取用户的收藏城市列表
     * @param userId 用户ID
     * @return 收藏城市列表
     */
    List<String> getFavoriteLocations(String userId);
    
    /**
     * 添加天气提醒
     * @param userId 用户ID
     * @param alert 天气提醒
     * @return 更新后的用户信息
     */
    User addWeatherAlert(String userId, WeatherAlert alert);
    
    /**
     * 删除天气提醒
     * @param userId 用户ID
     * @param alertId 天气提醒ID
     * @return 更新后的用户信息
     */
    User removeWeatherAlert(String userId, String alertId);
    
    /**
     * 获取用户的天气提醒列表
     * @param userId 用户ID
     * @return 天气提醒列表
     */
    List<WeatherAlert> getWeatherAlerts(String userId);
    
    /**
    /**
     * 更新用户个人信息
     * @param userId 用户ID
     * @param email 邮箱地址
     * @return 更新后的用户信息
     */
    User updateUserProfile(String userId, String email);
    
    /**
     * 添加用户城市（兼容方法）
     * @param userId 用户ID
     * @param city 城市名称
     */
    void addUserCity(String userId, String city);
    
    /**
     * 移除用户城市（兼容方法）
     * @param userId 用户ID
     * @param city 城市名称
     */
    void removeUserCity(String userId, String city);
    
    /**
     * 获取用户城市列表（兼容方法）
     * @param userId 用户ID
     * @return 城市列表
     */
    List<String> getUserCities(String userId);
}