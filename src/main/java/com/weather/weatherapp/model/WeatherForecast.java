package com.weather.weatherapp.model;

import java.util.List;

/**
 * 天气预报整体数据模型
 */
public class WeatherForecast {
    private CurrentWeather current; // 当前天气
    private List<ForecastDay> forecast; // 预报列表
    private List<LifeIndex> lifeIndices; // 生活指数
    private List<WeatherWarning> warnings; // 天气预警

    public CurrentWeather getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeather current) {
        this.current = current;
    }

    public List<ForecastDay> getForecast() {
        return forecast;
    }

    public void setForecast(List<ForecastDay> forecast) {
        this.forecast = forecast;
    }

    public List<LifeIndex> getLifeIndices() {
        return lifeIndices;
    }

    public void setLifeIndices(List<LifeIndex> lifeIndices) {
        this.lifeIndices = lifeIndices;
    }

    public List<WeatherWarning> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<WeatherWarning> warnings) {
        this.warnings = warnings;
    }
}