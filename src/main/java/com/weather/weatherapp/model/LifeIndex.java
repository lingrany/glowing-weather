package com.weather.weatherapp.model;

/**
 * 生活指数数据模型
 */
public class LifeIndex {
    private String name; // 指数名称（如：穿衣、运动等）
    private String category; // 指数类别
    private String value; // 指数值（如：舒适、较适宜等）
    private String description; // 详细描述

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}