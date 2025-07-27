# 🌟 Glowing Weather - 一个功能全面的天气预报网站

这是一个使用 Java Spring Boot 和 Thymeleaf 构建的现代化、功能丰富的天气预报网站。它不仅提供实时天气、未来7天预报，还集成了智能天气预警系统、交互式地图和用户个性化设置等高级功能。

## ✨ 功能亮点

- **实时天气与预报**：查询全国主要城市的实时天气状况和未来7天的详细天气预报。
- **智能预警系统**：可自定义高温、低温、雨、雪、大风等多种天气预警，并通过邮件和应用内通知及时提醒。
- **交互式地图**：在地图上直观地查看各地的天气信息，支持多图层切换。
- **生活指数**：提供穿衣、运动、洗车、旅游等实用生活建议。
- **用户个性化**：支持多城市管理、自定义天气提醒和个人信息设置。
- **现代化UI**：采用响应式设计，在PC和移动设备上均有出色表现。

## 🛠️ 技术栈

- **后端**: Spring Boot 3.x, Spring MVC
- **前端**: Thymeleaf, HTML5, CSS3
- **数据存储**: 内存存储 (易于扩展至数据库)
- **构建工具**: Maven
- **开发语言**: Java 17

## 🚀 快速启动

1.  **克隆项目到本地**:
    ```bash
    git clone https://github.com/lingrany/glowing-weather.git
    cd glowing-weather
    ```

2.  **配置API密钥**:
    打开 `src/main/resources/application.properties` 文件，填入您申请的第三方天气API密钥。
    ```properties
    weather.api.key=your_api_key_here
    weather.api.url=https://api.seniverse.com/v3
    ```

3.  **运行项目**:
    在项目根目录下，使用Maven运行以下命令：
    ```bash
    mvn spring-boot:run
    ```

4.  **访问应用**:
    项目启动后，在浏览器中打开 `http://localhost:9999` 即可访问。

## 📂 项目结构

为了保证代码的清晰性、可维护性和可扩展性，项目采用了经典的分层架构：

```
glowing-weather/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/weather/weatherapp/
│   │   │       ├── controller/      # 控制器层 (处理HTTP请求)
│   │   │       │   ├── UserController.java
│   │   │       │   ├── WeatherController.java
│   │   │       │   └── ...
│   │   │       ├── service/         # 业务逻辑层 (接口)
│   │   │       │   ├── UserService.java
│   │   │       │   └── WeatherService.java
│   │   │       ├── service/impl/    # 业务逻辑层 (实现)
│   │   │       │   ├── UserServiceImpl.java
│   │   │       │   └── WeatherServiceImpl.java
│   │   │       ├── repository/      # 数据访问层 (用于数据存取)
│   │   │       │   ├── UserRepository.java
│   │   │       │   └── InMemoryUserRepository.java
│   │   │       ├── model/           # 领域模型 (定义核心业务对象)
│   │   │       │   ├── User.java
│   │   │       │   └── WeatherForecast.java
│   │   │       ├── dto/             # 数据传输对象 (用于层间数据传递)
│   │   │       │   └── UserProfileDto.java
│   │   │       └── WeatherAppApplication.java  # Spring Boot 启动类
│   │   └── resources/
│   │       ├── static/            # 静态资源 (CSS, JS, 图片)
│   │       ├── templates/         # Thymeleaf 模板文件
│   │       └── application.properties  # 配置文件
├── .gitignore                     # Git忽略文件配置
├── pom.xml                        # Maven项目配置文件
└── README.md                      # 项目说明文件
```

---
*这个项目旨在展示一个结构清晰、代码规范的Web应用范例。*