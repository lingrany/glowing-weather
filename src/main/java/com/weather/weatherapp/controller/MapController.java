package com.weather.weatherapp.controller;

import com.weather.weatherapp.model.WeatherForecast;
import com.weather.weatherapp.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MapController {

    private static final Logger logger = LoggerFactory.getLogger(MapController.class);
    
    private final WeatherService weatherService;
    private final Map<String, double[]> cityCoordinates;

    public MapController(WeatherService weatherService) {
        this.weatherService = weatherService;
        this.cityCoordinates = new HashMap<>();
        initializeCityCoordinates();
    }

    private void initializeCityCoordinates() {
        // 中国大陆主要城市
        cityCoordinates.put("北京", new double[]{39.9042, 116.4074});
        cityCoordinates.put("Beijing", new double[]{39.9042, 116.4074});
        cityCoordinates.put("上海", new double[]{31.2304, 121.4737});
        cityCoordinates.put("Shanghai", new double[]{31.2304, 121.4737});
        cityCoordinates.put("广州", new double[]{23.1291, 113.2644});
        cityCoordinates.put("Guangzhou", new double[]{23.1291, 113.2644});
        cityCoordinates.put("深圳", new double[]{22.5431, 114.0579});
        cityCoordinates.put("Shenzhen", new double[]{22.5431, 114.0579});
        cityCoordinates.put("杭州", new double[]{30.2741, 120.1551});
        cityCoordinates.put("Hangzhou", new double[]{30.2741, 120.1551});
        cityCoordinates.put("南京", new double[]{32.0603, 118.7969});
        cityCoordinates.put("Nanjing", new double[]{32.0603, 118.7969});
        cityCoordinates.put("成都", new double[]{30.5728, 104.0668});
        cityCoordinates.put("Chengdu", new double[]{30.5728, 104.0668});
        cityCoordinates.put("重庆", new double[]{29.5630, 106.5516});
        cityCoordinates.put("Chongqing", new double[]{29.5630, 106.5516});
        cityCoordinates.put("西安", new double[]{34.3416, 108.9398});
        cityCoordinates.put("Xian", new double[]{34.3416, 108.9398});
        cityCoordinates.put("武汉", new double[]{30.5928, 114.3055});
        cityCoordinates.put("Wuhan", new double[]{30.5928, 114.3055});
        cityCoordinates.put("天津", new double[]{39.3434, 117.3616});
        cityCoordinates.put("Tianjin", new double[]{39.3434, 117.3616});
        
        // 港澳台地区
        cityCoordinates.put("香港", new double[]{22.3193, 114.1694});
        cityCoordinates.put("Hong Kong", new double[]{22.3193, 114.1694});
        cityCoordinates.put("澳门", new double[]{22.1987, 113.5439});
        cityCoordinates.put("Macau", new double[]{22.1987, 113.5439});
        cityCoordinates.put("Macao", new double[]{22.1987, 113.5439});
        cityCoordinates.put("台北", new double[]{25.0330, 121.5654});
        cityCoordinates.put("Taipei", new double[]{25.0330, 121.5654});
        
        // 全国省会城市
        cityCoordinates.put("石家庄", new double[]{38.0428, 114.5149});
        cityCoordinates.put("Shijiazhuang", new double[]{38.0428, 114.5149});
        cityCoordinates.put("太原", new double[]{37.8706, 112.5489});
        cityCoordinates.put("Taiyuan", new double[]{37.8706, 112.5489});
        cityCoordinates.put("沈阳", new double[]{41.8057, 123.4315});
        cityCoordinates.put("Shenyang", new double[]{41.8057, 123.4315});
        cityCoordinates.put("长春", new double[]{43.8171, 125.3235});
        cityCoordinates.put("Changchun", new double[]{43.8171, 125.3235});
        cityCoordinates.put("哈尔滨", new double[]{45.8038, 126.5349});
        cityCoordinates.put("Harbin", new double[]{45.8038, 126.5349});
        cityCoordinates.put("呼和浩特", new double[]{40.8414, 111.7519});
        cityCoordinates.put("Hohhot", new double[]{40.8414, 111.7519});
        cityCoordinates.put("银川", new double[]{38.4872, 106.2309});
        cityCoordinates.put("Yinchuan", new double[]{38.4872, 106.2309});
        cityCoordinates.put("兰州", new double[]{36.0611, 103.8343});
        cityCoordinates.put("Lanzhou", new double[]{36.0611, 103.8343});
        cityCoordinates.put("西宁", new double[]{36.6171, 101.7782});
        cityCoordinates.put("Xining", new double[]{36.6171, 101.7782});
        cityCoordinates.put("乌鲁木齐", new double[]{43.8256, 87.6168});
        cityCoordinates.put("Urumqi", new double[]{43.8256, 87.6168});
        cityCoordinates.put("拉萨", new double[]{29.6520, 91.1721});
        cityCoordinates.put("Lhasa", new double[]{29.6520, 91.1721});
        cityCoordinates.put("昆明", new double[]{25.0389, 102.7183});
        cityCoordinates.put("Kunming", new double[]{25.0389, 102.7183});
        cityCoordinates.put("贵阳", new double[]{26.6470, 106.6302});
        cityCoordinates.put("Guiyang", new double[]{26.6470, 106.6302});
        cityCoordinates.put("南宁", new double[]{22.8170, 108.3669});
        cityCoordinates.put("Nanning", new double[]{22.8170, 108.3669});
        cityCoordinates.put("海口", new double[]{20.0444, 110.1989});
        cityCoordinates.put("Haikou", new double[]{20.0444, 110.1989});
        cityCoordinates.put("三亚", new double[]{18.2479, 109.5146});
        cityCoordinates.put("Sanya", new double[]{18.2479, 109.5146});
        cityCoordinates.put("福州", new double[]{26.0745, 119.2965});
        cityCoordinates.put("Fuzhou", new double[]{26.0745, 119.2965});
        cityCoordinates.put("南昌", new double[]{28.6820, 115.8581});
        cityCoordinates.put("Nanchang", new double[]{28.6820, 115.8581});
        cityCoordinates.put("济南", new double[]{36.6512, 117.1201});
        cityCoordinates.put("Jinan", new double[]{36.6512, 117.1201});
        cityCoordinates.put("郑州", new double[]{34.7466, 113.6254});
        cityCoordinates.put("Zhengzhou", new double[]{34.7466, 113.6254});
        cityCoordinates.put("长沙", new double[]{28.2282, 112.9388});
        cityCoordinates.put("Changsha", new double[]{28.2282, 112.9388});
        
        // 其他重要城市
        cityCoordinates.put("青岛", new double[]{36.0986, 120.3719});
        cityCoordinates.put("Qingdao", new double[]{36.0986, 120.3719});
        cityCoordinates.put("大连", new double[]{38.9140, 121.6147});
        cityCoordinates.put("Dalian", new double[]{38.9140, 121.6147});
        cityCoordinates.put("厦门", new double[]{24.4798, 118.0894});
        cityCoordinates.put("Xiamen", new double[]{24.4798, 118.0894});
        cityCoordinates.put("珠海", new double[]{22.2711, 113.5767});
        cityCoordinates.put("Zhuhai", new double[]{22.2711, 113.5767});
        cityCoordinates.put("合肥", new double[]{31.8206, 117.2272});
        cityCoordinates.put("Hefei", new double[]{31.8206, 117.2272});
        cityCoordinates.put("苏州", new double[]{31.2989, 120.5853});
        cityCoordinates.put("Suzhou", new double[]{31.2989, 120.5853});
        cityCoordinates.put("无锡", new double[]{31.4912, 120.3124});
        cityCoordinates.put("Wuxi", new double[]{31.4912, 120.3124});
        cityCoordinates.put("宁波", new double[]{29.8683, 121.5440});
        cityCoordinates.put("Ningbo", new double[]{29.8683, 121.5440});
        cityCoordinates.put("温州", new double[]{27.9936, 120.6989});
        cityCoordinates.put("Wenzhou", new double[]{27.9936, 120.6989});
        cityCoordinates.put("佛山", new double[]{23.0218, 113.1219});
        cityCoordinates.put("Foshan", new double[]{23.0218, 113.1219});
        cityCoordinates.put("东莞", new double[]{23.0489, 113.7447});
        cityCoordinates.put("Dongguan", new double[]{23.0489, 113.7447});
        
        // 河北省城市
        cityCoordinates.put("唐山", new double[]{39.6243, 118.1944});
        cityCoordinates.put("Tangshan", new double[]{39.6243, 118.1944});
        cityCoordinates.put("秦皇岛", new double[]{39.9398, 119.6006});
        cityCoordinates.put("Qinhuangdao", new double[]{39.9398, 119.6006});
        cityCoordinates.put("邯郸", new double[]{36.6253, 114.5389});
        cityCoordinates.put("Handan", new double[]{36.6253, 114.5389});
        cityCoordinates.put("保定", new double[]{38.8740, 115.4648});
        cityCoordinates.put("Baoding", new double[]{38.8740, 115.4648});
        cityCoordinates.put("张家口", new double[]{40.8111, 114.8794});
        cityCoordinates.put("Zhangjiakou", new double[]{40.8111, 114.8794});
        cityCoordinates.put("承德", new double[]{40.9543, 117.9622});
        cityCoordinates.put("Chengde", new double[]{40.9543, 117.9622});
        cityCoordinates.put("廊坊", new double[]{39.5238, 116.7038});
        cityCoordinates.put("Langfang", new double[]{39.5238, 116.7038});
        cityCoordinates.put("沧州", new double[]{38.3037, 116.8575});
        cityCoordinates.put("Cangzhou", new double[]{38.3037, 116.8575});
        cityCoordinates.put("衡水", new double[]{37.7161, 115.6756});
        cityCoordinates.put("Hengshui", new double[]{37.7161, 115.6756});
        cityCoordinates.put("邢台", new double[]{37.0682, 114.5086});
        cityCoordinates.put("Xingtai", new double[]{37.0682, 114.5086});
    }

    /**
     * 获取城市坐标
     * 支持精确匹配和模糊匹配
     */
    private double[] getCityCoordinates(String city) {
        // 精确匹配
        if (cityCoordinates.containsKey(city)) {
            logger.info("精确匹配找到城市坐标: {} -> [{}, {}]", city, cityCoordinates.get(city)[0], cityCoordinates.get(city)[1]);
            return cityCoordinates.get(city);
        }
        
        // 模糊匹配（去掉"市"、"县"、"区"等后缀）
        String simplifiedCity = city.replaceAll("[市县区]$", "");
        for (String key : cityCoordinates.keySet()) {
            String simplifiedKey = key.replaceAll("[市县区]$", "");
            if (key.contains(simplifiedCity) || simplifiedCity.contains(simplifiedKey)) {
                logger.info("模糊匹配找到城市坐标: {} -> {} -> [{}, {}]", city, key, cityCoordinates.get(key)[0], cityCoordinates.get(key)[1]);
                return cityCoordinates.get(key);
            }
        }
        
        // 如果找不到，返回北京坐标作为默认值
        logger.warn("未找到城市坐标，使用默认坐标: {} -> 北京", city);
        return cityCoordinates.get("北京");
    }

    @GetMapping("/map")
    public String showMap(@RequestParam(value = "city", defaultValue = "Beijing") String city, Model model) {
        WeatherForecast forecast = weatherService.getWeatherForecast(city);
        
        // 获取城市坐标
        double[] coordinates = getCityCoordinates(city);
        
        if (forecast != null) {
            model.addAttribute("forecast", forecast);
            model.addAttribute("city", city);
            model.addAttribute("latitude", coordinates[0]);
            model.addAttribute("longitude", coordinates[1]);
            logger.info("成功显示城市地图: {} -> [{}, {}]", city, coordinates[0], coordinates[1]);
        } else {
            model.addAttribute("error", "无法获取城市 '" + city + "' 的天气信息。请检查城市名称或稍后再试。");
            model.addAttribute("city", city);
            model.addAttribute("latitude", coordinates[0]);
            model.addAttribute("longitude", coordinates[1]);
            logger.warn("天气数据获取失败，但仍显示地图: {} -> [{}, {}]", city, coordinates[0], coordinates[1]);
        }
        return "map";
    }
}