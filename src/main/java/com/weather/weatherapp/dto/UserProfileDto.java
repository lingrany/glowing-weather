package com.weather.weatherapp.dto;

import com.weather.weatherapp.model.WeatherAlert;
import lombok.Data;

import java.util.List;

/**
 * 用户个人设置页面的数据传输对象
 */
@Data
public class UserProfileDto {

    private String userId;
    private String username;
    private String email;
    private List<String> cities;
    private List<WeatherAlert> alerts;
    private List<String> recentNotifications;

}