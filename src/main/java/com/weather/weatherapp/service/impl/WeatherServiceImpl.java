package com.weather.weatherapp.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.weatherapp.model.*;
import com.weather.weatherapp.service.WeatherIconService;
import com.weather.weatherapp.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final WeatherIconService weatherIconService;

    public WeatherServiceImpl(WeatherIconService weatherIconService) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.weatherIconService = weatherIconService;
    }

    @Override
    public WeatherForecast getWeatherForecast(String city) {
        try {
            // 获取实时天气
            String nowUrl = String.format("%s/weather/now.json?key=%s&location=%s&language=zh-Hans&unit=c",
                    apiUrl, apiKey, city);
            String nowResponse = restTemplate.getForObject(nowUrl, String.class);

            // 获取天气预报
            String forecastUrl = String.format("%s/weather/daily.json?key=%s&location=%s&language=zh-Hans&unit=c&days=7",
                    apiUrl, apiKey, city);
            String forecastResponse = restTemplate.getForObject(forecastUrl, String.class);

            // 获取生活指数
            String lifeUrl = String.format("%s/life/suggestion.json?key=%s&location=%s&language=zh-Hans",
                    apiUrl, apiKey, city);
            String lifeResponse = restTemplate.getForObject(lifeUrl, String.class);

            // 获取天气预警
            String warningUrl = String.format("%s/weather/alarm.json?key=%s&location=%s",
                    apiUrl, apiKey, city);
            String warningResponse = restTemplate.getForObject(warningUrl, String.class);

            logger.info("成功获取城市的天气数据: {}", city);
            return parseWeatherResponse(nowResponse, forecastResponse, lifeResponse, warningResponse);
        } catch (HttpClientErrorException e) {
            logger.error("获取城市天气数据时出错: {}. 状态码: {}", city, e.getStatusCode());
            return null;
        } catch (Exception e) {
            logger.error("获取城市天气数据时发生意外错误: {}", city, e);
            return null;
        }
    }

    private WeatherForecast parseWeatherResponse(String nowResponse, String forecastResponse,
                                               String lifeResponse, String warningResponse) throws IOException {
        JsonNode nowRoot = objectMapper.readTree(nowResponse);
        JsonNode forecastRoot = objectMapper.readTree(forecastResponse);
        JsonNode lifeRoot = objectMapper.readTree(lifeResponse);
        JsonNode warningRoot = objectMapper.readTree(warningResponse);

        // 解析实时天气
        JsonNode resultsNow = nowRoot.path("results").get(0);
        JsonNode locationNode = resultsNow.path("location");
        JsonNode nowNode = resultsNow.path("now");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setLocationName(locationNode.path("name").asText());
        currentWeather.setLastUpdated(nowNode.path("last_update").asText());
        currentWeather.setTempC(Double.parseDouble(nowNode.path("temperature").asText()));
        currentWeather.setConditionText(nowNode.path("text").asText());

        // 使用图标服务获取图标名称
        String iconCode = nowNode.path("code").asText();
        currentWeather.setConditionIcon(weatherIconService.getIconName(iconCode));

        currentWeather.setWindKph(Double.parseDouble(nowNode.path("wind_speed").asText()));
        currentWeather.setHumidity(Integer.parseInt(nowNode.path("humidity").asText()));
        currentWeather.setFeelslikeC(Double.parseDouble(nowNode.path("feels_like").asText()));

        // 解析天气预报
        JsonNode resultsForecast = forecastRoot.path("results").get(0);
        JsonNode dailyNode = resultsForecast.path("daily");
        List<ForecastDay> forecastDays = new ArrayList<>();

        if (dailyNode.isArray()) {
            for (JsonNode dayNode : dailyNode) {
                ForecastDay forecastDay = new ForecastDay();
                forecastDay.setDate(dayNode.path("date").asText());
                forecastDay.setMaxTempC(Double.parseDouble(dayNode.path("high").asText()));
                forecastDay.setMinTempC(Double.parseDouble(dayNode.path("low").asText()));
                forecastDay.setConditionText(dayNode.path("text_day").asText());

                // 使用图标服务获取图标名称
                String dayIconCode = dayNode.path("code_day").asText();
                forecastDay.setConditionIcon(weatherIconService.getIconName(dayIconCode));

                forecastDays.add(forecastDay);
            }
        }

        // 解析生活指数
        List<LifeIndex> lifeIndices = new ArrayList<>();
        if (lifeRoot.has("results") && lifeRoot.path("results").size() > 0) {
            JsonNode resultsLife = lifeRoot.path("results").get(0);
            JsonNode suggestionNode = resultsLife.path("suggestion");

            addLifeIndex(lifeIndices, "穿衣", "dressing", suggestionNode.path("dressing"));
            addLifeIndex(lifeIndices, "运动", "sport", suggestionNode.path("sport"));
            addLifeIndex(lifeIndices, "洗车", "car_washing", suggestionNode.path("car_washing"));
            addLifeIndex(lifeIndices, "旅游", "travel", suggestionNode.path("travel"));
            addLifeIndex(lifeIndices, "感冒", "flu", suggestionNode.path("flu"));
            addLifeIndex(lifeIndices, "紫外线", "uv", suggestionNode.path("uv"));
        }

        // 解析天气预警
        List<WeatherWarning> warnings = new ArrayList<>();
        if (warningRoot.has("results") && warningRoot.path("results").size() > 0) {
            JsonNode resultsWarning = warningRoot.path("results").get(0);
            JsonNode alarmsNode = resultsWarning.path("alarms");

            if (alarmsNode.isArray()) {
                for (JsonNode alarmNode : alarmsNode) {
                    WeatherWarning warning = new WeatherWarning();
                    warning.setId(UUID.randomUUID().toString());
                    warning.setTitle(alarmNode.path("title").asText());
                    warning.setType(alarmNode.path("type").asText());
                    warning.setLevel(alarmNode.path("level").asText());
                    warning.setContent(alarmNode.path("description").asText());
                    warning.setPubTime(alarmNode.path("pub_date").asText());
                    warnings.add(warning);
                }
            }
        }

        WeatherForecast weatherForecast = new WeatherForecast();
        weatherForecast.setCurrent(currentWeather);
        weatherForecast.setForecast(forecastDays);
        weatherForecast.setLifeIndices(lifeIndices);
        weatherForecast.setWarnings(warnings);

        return weatherForecast;
    }

    private void addLifeIndex(List<LifeIndex> indices, String name, String category, JsonNode node) {
        if (node != null && !node.isMissingNode()) {
            LifeIndex index = new LifeIndex();
            index.setName(name);
            index.setCategory(category);
            index.setValue(node.path("brief").asText());
            index.setDescription(node.path("details").asText());
            indices.add(index);
        }
    }

}