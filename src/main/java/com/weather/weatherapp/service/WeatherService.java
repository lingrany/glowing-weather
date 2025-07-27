package com.weather.weatherapp.service;

import com.weather.weatherapp.model.WeatherForecast;

/**
 * 天气服务接口
 */
public interface WeatherService {
    /**
     * 获取指定城市的天气预报
     * @param city 城市名称
     * @return 天气预报数据
     */
    WeatherForecast getWeatherForecast(String city);
    
}