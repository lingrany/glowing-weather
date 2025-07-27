package com.weather.weatherapp.model;

/**
 * 天气预警数据模型
 */
public class WeatherWarning {
    private String id;
    private String title; // 预警标题
    private String type; // 预警类型（如：暴雨、台风、高温等）
    private String level; // 预警等级（如：蓝色、黄色、橙色、红色）
    private String content; // 预警内容
    private String pubTime; // 发布时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }
}