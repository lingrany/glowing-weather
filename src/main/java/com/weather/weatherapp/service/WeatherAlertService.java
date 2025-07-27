package com.weather.weatherapp.service;

import com.weather.weatherapp.model.User;
import com.weather.weatherapp.model.WeatherAlert;
import com.weather.weatherapp.model.WeatherForecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * 天气提醒服务
 */
@Service
public class WeatherAlertService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private WeatherService weatherService;
    
    @Autowired
    private EmailService emailService;
    
    // 存储实时通知消息
    // 存储实时通知消息
    private Map<String, List<String>> recentNotifications = new ConcurrentHashMap<>();
    
    // 存储已发送的预警，避免重复发送
    private Map<String, Long> lastAlertTime = new ConcurrentHashMap<>();

    /**
     * 每5分钟检查一次天气提醒
     */
    @Scheduled(fixedRate = 300000) // 每5分钟执行一次
    public void checkWeatherAlerts() {
        System.out.println("开始检查天气提醒...");
        
        // 获取默认用户的提醒列表
        List<WeatherAlert> alerts = userService.getWeatherAlerts("user123");
        
        for (WeatherAlert alert : alerts) {
            try {
                checkSingleAlert(alert);
            } catch (Exception e) {
                System.err.println("检查提醒时出错: " + e.getMessage());
            }
        }
    }

    /**
     * 检查单个提醒
     */
    private void checkSingleAlert(WeatherAlert alert) {
        try {
            WeatherForecast forecast = weatherService.getWeatherForecast(alert.getCity());
            if (forecast == null || forecast.getCurrent() == null) {
                return;
            }

            boolean shouldAlert = false;
            String alertMessage = "";

            // 根据不同的天气条件检查是否需要提醒
            switch (alert.getCondition()) {
                case "高温":
                    if (forecast.getCurrent().getTempC() > extractTemperatureThreshold(alert.getThreshold())) {
                        shouldAlert = true;
                        alertMessage = String.format("%s当前温度%.1f°C，超过了您设置的阈值！", 
                            alert.getCity(), forecast.getCurrent().getTempC());
                    }
                    break;
                case "低温":
                    if (forecast.getCurrent().getTempC() < extractTemperatureThreshold(alert.getThreshold())) {
                        shouldAlert = true;
                        alertMessage = String.format("%s当前温度%.1f°C，低于了您设置的阈值！", 
                            alert.getCity(), forecast.getCurrent().getTempC());
                    }
                    break;
                case "雨":
                    if (forecast.getCurrent().getConditionText().contains("雨")) {
                        shouldAlert = true;
                        alertMessage = String.format("%s当前天气：%s，请注意携带雨具！", 
                            alert.getCity(), forecast.getCurrent().getConditionText());
                    }
                    break;
                case "雪":
                    if (forecast.getCurrent().getConditionText().contains("雪")) {
                        shouldAlert = true;
                        alertMessage = String.format("%s当前天气：%s，请注意保暖和出行安全！", 
                            alert.getCity(), forecast.getCurrent().getConditionText());
                    }
                    break;
                case "大风":
                    if (forecast.getCurrent().getWindKph() > extractWindThreshold(alert.getThreshold())) {
                        shouldAlert = true;
                        alertMessage = String.format("%s当前风速%.1fkm/h，超过了您设置的阈值！", 
                            alert.getCity(), forecast.getCurrent().getWindKph());
                    }
                    break;
            }

            if (shouldAlert) {
                sendAlert(alert, alertMessage);
            }

        } catch (Exception e) {
            System.err.println("检查城市 " + alert.getCity() + " 的天气提醒时出错: " + e.getMessage());
        }
    }

    /**
    /**
     * 发送提醒
     */
    private void sendAlert(WeatherAlert alert, String message) {
        // 检查是否在30分钟内已经发送过相同的预警
        String alertKey = alert.getCity() + "_" + alert.getCondition();
        long currentTime = System.currentTimeMillis();
        Long lastTime = lastAlertTime.get(alertKey);
        
        if (lastTime != null && (currentTime - lastTime) < 30 * 60 * 1000) {
            // 30分钟内已发送过，跳过
            return;
        }
        
        System.out.println("🚨 天气提醒触发！");
        System.out.println("城市: " + alert.getCity());
        System.out.println("条件: " + alert.getCondition());
        System.out.println("消息: " + message);
        System.out.println("通知方式: " + alert.getNotificationType());

        // 记录发送时间
        lastAlertTime.put(alertKey, currentTime);

        // 将预警信息添加到通知列表中
        String userId = alert.getUserId();
        String alertText = "🚨 " + alert.getCondition() + "预警：" + message;
        
        List<String> userNotifications = recentNotifications.computeIfAbsent(userId, k -> new ArrayList<>());
        
        // 检查是否已存在相同的预警信息
        if (!userNotifications.contains(alertText)) {
            userNotifications.add(alertText);
            
            // 保持通知列表不超过5条
            if (userNotifications.size() > 5) {
                userNotifications.remove(0);
            }
        }

        // 根据通知方式发送提醒
        switch (alert.getNotificationType()) {
            case "应用内通知":
                sendInAppNotification(message);
                break;
            case "邮件":
                sendEmailNotification(alert, message);
                break;
        }
    }

    /**
     * 发送应用内通知
     */
    private void sendInAppNotification(String message) {
        System.out.println("📱 应用内通知: " + message);
    }

    /**
     * 发送邮件通知
     */
    private void sendEmailNotification(WeatherAlert alert, String message) {
        User user = userService.getUser("user123");
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            System.out.println("📧 正在发送邮件通知到: " + user.getEmail());
            try {
                emailService.sendWeatherAlert(user.getEmail(), alert.getCity(), message);
                System.out.println("✅ 邮件发送成功");
            } catch (Exception e) {
                System.err.println("❌ 邮件发送失败: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ 用户未设置邮箱地址，无法发送邮件通知");
        }
    }

    /**
     * 从阈值字符串中提取温度值
     */
    private double extractTemperatureThreshold(String threshold) {
        try {
            String[] parts = threshold.split("[><=]");
            for (String part : parts) {
                String cleaned = part.replaceAll("[^0-9.-]", "").trim();
                if (!cleaned.isEmpty()) {
                    return Double.parseDouble(cleaned);
                }
            }
        } catch (Exception e) {
            System.err.println("解析温度阈值失败: " + threshold);
        }
        return 30.0; // 默认值
    }

    /**
     * 从阈值字符串中提取风速值
     */
    private double extractWindThreshold(String threshold) {
        try {
            String[] parts = threshold.split("[><=]");
            for (String part : parts) {
                String cleaned = part.replaceAll("[^0-9.-]", "").trim();
                if (!cleaned.isEmpty()) {
                    return Double.parseDouble(cleaned);
                }
            }
        } catch (Exception e) {
            System.err.println("解析风速阈值失败: " + threshold);
        }
        return 20.0; // 默认值
    }

    /**
     * 获取用户的天气提醒列表
     */
    public List<WeatherAlert> getUserAlerts(String userId) {
        return userService.getWeatherAlerts(userId);
    }

    /**
     * 获取最近的通知消息
     */
    public List<String> getRecentNotifications(String userId) {
        List<String> notifications = recentNotifications.getOrDefault(userId, new ArrayList<>());
        
        // 如果没有预警信息，添加一些状态信息
        if (notifications.isEmpty()) {
            List<String> statusInfo = new ArrayList<>();
            statusInfo.add("✅ 系统运行正常，暂无天气预警");
            statusInfo.add("📊 天气数据实时更新中");
            return statusInfo;
        }
        
        // 返回最新的预警信息（倒序显示）
        List<String> result = new ArrayList<>(notifications);
        java.util.Collections.reverse(result);
        return result;
    }

    /**
     * 添加天气提醒
     */
    public void addAlert(String userId, String city, String condition, String threshold, String notificationMethod) {
        WeatherAlert alert = new WeatherAlert();
        alert.setId("alert_" + System.currentTimeMillis());
        alert.setUserId(userId);
        alert.setCity(city);
        alert.setCondition(condition);
        alert.setThreshold(threshold);
        alert.setNotificationType(notificationMethod);
        alert.setEnabled(true);
        
        userService.addWeatherAlert(userId, alert);
        System.out.println("添加天气提醒: " + city + " - " + condition);
    }

    /**
     * 移除天气提醒
     */
    public void removeAlert(String alertId) {
        userService.removeWeatherAlert("user123", alertId);
        System.out.println("移除天气提醒: " + alertId);
    }

    /**
     * 测试邮件通知
     */
    public void testEmailNotification(String email) {
        try {
            emailService.sendWeatherAlert(email, "测试城市", "这是一条测试邮件通知，用于验证邮件功能是否正常工作。");
            System.out.println("测试邮件发送成功到: " + email);
        } catch (Exception e) {
            System.err.println("测试邮件发送失败: " + e.getMessage());
            throw e;
        }
    }
}