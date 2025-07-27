package com.weather.weatherapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据模型
 */
public class User {
    private String userId;
    private String username;
    private String email;
    private List<String> cities = new ArrayList<>();
    private List<WeatherAlert> alerts = new ArrayList<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public List<WeatherAlert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<WeatherAlert> alerts) {
        this.alerts = alerts;
    }
}