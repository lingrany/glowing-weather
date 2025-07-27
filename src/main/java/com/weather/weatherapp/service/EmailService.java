package com.weather.weatherapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮件发送服务
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单文本邮件
     */
    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("2503388963@qq.com"); // 发送方邮箱，必须与配置的用户名相同
            message.setTo(to); // 接收方邮箱
            message.setSubject(subject); // 邮件主题
            message.setText(text); // 邮件内容
            
            mailSender.send(message);
            System.out.println("✅ 邮件发送成功到: " + to);
        } catch (Exception e) {
            System.err.println("❌ 邮件发送失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 发送天气提醒邮件
     */
    public void sendWeatherAlert(String to, String city, String alertMessage) {
        String subject = "🌤️ 天气提醒 - " + city;
        String content = String.format(
            "您好！\n\n" +
            "这是来自天气预报系统的提醒：\n\n" +
            "%s\n\n" +
            "请注意天气变化，做好相应准备。\n\n" +
            "祝您生活愉快！\n" +
            "天气预报系统",
            alertMessage
        );
        
        sendSimpleEmail(to, subject, content);
    }
}