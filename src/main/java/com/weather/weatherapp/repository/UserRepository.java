package com.weather.weatherapp.repository;

import com.weather.weatherapp.model.User;
import com.weather.weatherapp.model.WeatherAlert;

import java.util.List;

/**
 * 用户数据访问接口
 */
public interface UserRepository {

    /**
     * 根据用户ID获取用户
     * @param userId 用户ID
     * @return 用户对象
     */
    User findById(String userId);

    /**
     * 保存或更新用户信息
     * @param user 用户对象
     * @return 保存后的用户对象
     */
    User save(User user);

    /**
     * 为用户添加收藏城市
     * @param userId 用户ID
     * @param city 城市名称
     */
    void addCity(String userId, String city);

    /**
     * 为用户移除收藏城市
     * @param userId 用户ID
     * @param city 城市名称
     */
    void removeCity(String userId, String city);

    /**
     * 获取用户的所有收藏城市
     * @param userId 用户ID
     * @return 城市列表
     */
    List<String> findCitiesByUserId(String userId);

    /**
     * 为用户添加天气提醒
     * @param userId 用户ID
     * @param alert 天气提醒对象
     */
    void addAlert(String userId, WeatherAlert alert);

    /**
     * 为用户移除天气提醒
     * @param userId 用户ID
     * @param alertId 提醒ID
     */
    void removeAlert(String userId, String alertId);

    /**
     * 获取用户的所有天气提醒
     * @param userId 用户ID
     * @return 天气提醒列表
     */
    List<WeatherAlert> findAlertsByUserId(String userId);
}