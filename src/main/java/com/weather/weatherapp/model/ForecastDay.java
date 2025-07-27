package com.weather.weatherapp.model;

/**
 * 预报天气中的单日数据模型
 */
public class ForecastDay {
    private String date; // 日期
    private double maxTempC; // 最高温度（摄氏）
    private double minTempC; // 最低温度（摄氏）
    private String conditionText; // 天气状况文字描述
    private String conditionIcon; // 天气状况图标

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMaxTempC() {
        return maxTempC;
    }

    public void setMaxTempC(double maxTempC) {
        this.maxTempC = maxTempC;
    }

    public double getMinTempC() {
        return minTempC;
    }

    public void setMinTempC(double minTempC) {
        this.minTempC = minTempC;
    }

    public String getConditionText() {
        return conditionText;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public String getConditionIcon() {
        return conditionIcon;
    }

    public void setConditionIcon(String conditionIcon) {
        this.conditionIcon = conditionIcon;
    }
}