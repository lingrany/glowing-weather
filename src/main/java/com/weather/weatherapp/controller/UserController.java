package com.weather.weatherapp.controller;

import com.weather.weatherapp.dto.UserProfileDto;
import com.weather.weatherapp.model.User;
import com.weather.weatherapp.model.WeatherAlert;
import com.weather.weatherapp.service.EmailService;
import com.weather.weatherapp.service.UserService;
import com.weather.weatherapp.service.WeatherAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final WeatherAlertService weatherAlertService;

    @Autowired
    public UserController(UserService userService, EmailService emailService, WeatherAlertService weatherAlertService) {
        this.userService = userService;
        this.emailService = emailService;
        this.weatherAlertService = weatherAlertService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        String userId = "user123"; // 模拟用户ID
        User user = userService.getUser(userId);

        // 创建DTO并填充数据
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUserId(user.getUserId());
        userProfileDto.setUsername(user.getUsername());
        userProfileDto.setEmail(user.getEmail());
        userProfileDto.setCities(user.getCities());
        userProfileDto.setAlerts(user.getAlerts());
        
        // 获取并添加最近的通知
        List<String> recentNotifications = weatherAlertService.getRecentNotifications(userId);
        userProfileDto.setRecentNotifications(recentNotifications != null ? recentNotifications : new ArrayList<>());
        
        model.addAttribute("userProfile", userProfileDto);
        return "user/profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam String email) {
        userService.updateUserProfile("user123", email);
        return "redirect:/user/profile";
    }

    @PostMapping("/add-city")
    public String addCity(@RequestParam String city) {
        userService.addFavoriteLocation("user123", city);
        return "redirect:/user/profile";
    }

    @PostMapping("/remove-city")
    public String removeCity(@RequestParam String city) {
        userService.removeFavoriteLocation("user123", city);
        return "redirect:/user/profile";
    }

    @PostMapping("/add-alert")
    public String addAlert(@ModelAttribute WeatherAlert alert) {
        alert.setId(UUID.randomUUID().toString());
        userService.addWeatherAlert("user123", alert);
        return "redirect:/user/profile";
    }

    @PostMapping("/remove-alert")
    public String removeAlert(@RequestParam String alertId) {
        userService.removeWeatherAlert("user123", alertId);
        return "redirect:/user/profile";
    }

    @PostMapping("/send-test-email")
    public String sendTestEmail(@RequestParam String email) {
        try {
            emailService.sendTestEmail(email);
        } catch (Exception e) {
            // 可以在这里添加错误处理逻辑，例如重定向到带有错误消息的页面
            System.err.println("发送测试邮件失败: " + e.getMessage());
        }
        return "redirect:/user/profile";
    }
}

    @PostMapping("/add-alert")
    public String addAlert(@RequestParam String city,
                          @RequestParam String condition,
                          @RequestParam String threshold,
                          @RequestParam String notificationMethod,
                          RedirectAttributes redirectAttributes) {
        try {
            weatherAlertService.addAlert(DEFAULT_USER_ID, city, condition, threshold, notificationMethod);
            redirectAttributes.addFlashAttribute("message", "天气提醒已添加");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "添加提醒失败：" + e.getMessage());
        }
        return "redirect:/user/profile";
    }

    @PostMapping("/remove-alert")
    public String removeAlert(@RequestParam String alertId, RedirectAttributes redirectAttributes) {
        try {
            weatherAlertService.removeAlert(alertId);
            redirectAttributes.addFlashAttribute("message", "天气提醒已删除");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "删除提醒失败：" + e.getMessage());
        }
        return "redirect:/user/profile";
    }

    @PostMapping("/check-alerts")
    public String checkAlerts(RedirectAttributes redirectAttributes) {
        try {
            weatherAlertService.checkWeatherAlerts();
            redirectAttributes.addFlashAttribute("message", "天气提醒检查完成");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "检查提醒失败：" + e.getMessage());
        }
        return "redirect:/user/profile";
    }

    @PostMapping("/test-email")
    public String testEmail(RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUser(DEFAULT_USER_ID);
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                weatherAlertService.testEmailNotification(user.getEmail());
                redirectAttributes.addFlashAttribute("message", "测试邮件已发送到 " + user.getEmail());
            } else {
                redirectAttributes.addFlashAttribute("error", "请先设置邮箱地址");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "发送测试邮件失败：" + e.getMessage());
        }
        return "redirect:/user/profile";
    }
}