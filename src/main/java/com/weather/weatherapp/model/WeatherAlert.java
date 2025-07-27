package com.weather.weatherapp.model;

/**
 * 天气提醒数据模型
 */
public class WeatherAlert {
    private String id;
    private String userId; // 用户ID
    private String city; // 城市名称
    private String condition; // 天气条件（如：雨、雪、高温等）
    private String threshold; // 阈值（如：温度超过30度、降雨量超过50mm等）
    private String notificationType; // 通知类型（如：邮件、短信等）
    private boolean enabled; // 是否启用

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}