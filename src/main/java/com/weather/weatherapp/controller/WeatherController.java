package com.weather.weatherapp.controller;

import com.weather.weatherapp.model.WeatherForecast;
import com.weather.weatherapp.service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam("city") String city, Model model) {
        WeatherForecast forecast = weatherService.getWeatherForecast(city);
        if (forecast != null) {
            model.addAttribute("forecast", forecast);
            model.addAttribute("city", city);
        } else {
            model.addAttribute("error", "无法获取城市 '" + city + "' 的天气信息。请检查城市名称或稍后再试。");
        }
        return "weather";
    }
}