package com.weather.weatherapp.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 天气图标服务，用于将心知天气的代码映射到我们自己的图标
 */
@Service
public class WeatherIconService {

    private final Map<String, String> iconMap = new HashMap<>();

    public WeatherIconService() {
        // 初始化图标映射
        // 晴天
        iconMap.put("0", "sunny");
        iconMap.put("1", "sunny");
        iconMap.put("2", "sunny");
        iconMap.put("3", "sunny");
        
        // 多云
        iconMap.put("4", "cloudy");
        iconMap.put("5", "cloudy");
        iconMap.put("6", "cloudy");
        iconMap.put("7", "cloudy");
        iconMap.put("8", "cloudy");
        
        // 雨
        iconMap.put("9", "moderate_rain");
        iconMap.put("10", "moderate_rain");
        iconMap.put("11", "moderate_rain");
        iconMap.put("12", "moderate_rain");
        iconMap.put("13", "moderate_rain");
        iconMap.put("14", "moderate_rain");
        iconMap.put("15", "moderate_rain");
        iconMap.put("16", "moderate_rain");
        iconMap.put("17", "moderate_rain");
        iconMap.put("18", "moderate_rain");
        
        // 雪
        iconMap.put("19", "moderate_snow");
        iconMap.put("20", "moderate_snow");
        iconMap.put("21", "moderate_snow");
        iconMap.put("22", "moderate_snow");
        iconMap.put("23", "moderate_snow");
        iconMap.put("24", "moderate_snow");
        iconMap.put("25", "moderate_snow");
        
        // 雾
        iconMap.put("26", "fog");
        iconMap.put("27", "fog");
        iconMap.put("28", "fog");
        iconMap.put("29", "fog");
        iconMap.put("30", "fog");
        
        // 雷暴
        iconMap.put("31", "thunderstorm");
        iconMap.put("32", "thunderstorm");
        iconMap.put("33", "thunderstorm");
        iconMap.put("34", "thunderstorm");
        iconMap.put("35", "thunderstorm");
        iconMap.put("36", "thunderstorm");
        iconMap.put("37", "thunderstorm");
    }

    /**
     * 根据心知天气的代码获取对应的图标名称
     * @param code 心知天气的代码
     * @return 图标名称
     */
    public String getIconName(String code) {
        return iconMap.getOrDefault(code, "unknown");
    }
}