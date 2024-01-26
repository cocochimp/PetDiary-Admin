# PetDiary-Admin（开发中）
🎉基于SpringCloud Alibaba（gateway/nacos）、MyBatis-Plus、Redis、Druid、Knife4j、Vue、ElementUI、Uniapp微服务架构的宠物短视频社区小程序🐱🐶

> 项目地址：（基于ruoyi项目改造）

- SpringCloud管理系统后端：[https://github.com/cocochimp/PetDiary-Admin](https://github.com/cocochimp/PetDiary-Admin)
- vue管理系统前端：[https://github.com/cocochimp/PetDiary-UI](https://github.com/cocochimp/PetDiary-UI)
- uniapp小程序端：[https://github.com/cocochimp/PetDiary-uniapp](https://github.com/cocochimp/PetDiary-uniapp)

# 运行环境

> 注：以下的启动命令是mac端的，window/linux自行查阅

- Java8+
- mysql8.0+
- redis6.0+（低版本适配）
- nacos2.0+（记得配置mysql数据源，并且设置单机部署standalone）

```json
./startup.sh -m standalone
```

- sentinel1.8+（非必需）

# 项目架构

```properties
PetDiary-Admin
├── service-api                         // API接口模块
       └── service-api-system                  // 系统接口
├── common                              // 通用模块
       └── common-system                       // 核心模块
       └── common-redis                        // 缓存服务
       └── common-swagger                      // 系统接口
       └── common-security                     // 安全模块
├── service                             // 服务模块
       └── service-auth                        // 认证模块 [9200]
       └── service-core                        // 业务模块 [9501]
       └── service-function                    // 功能模块 [9202]
       └── service-gateway                     // 网关模块 [8080]
       └── service-monitor                     // 监控模块 [9100]
       └── service-system                      // 系统模块 [9201]
├──pom.xml
```

- 注：前端的端口为80

# 后端核心技术栈

| 依赖                | 作用             | 版本     |
| ------------------- | ---------------- | -------- |
| SpringBoot          | Java开发框架     | 2.7.x    |
| SpringCloud Alibaba | Java微服务架构   | 2021.0.x |
| Spring Framework    | Spring生态的容器 | 5.2.x    |
| Spring Security     | 安全框架         | 5.2.x    |
| SpringBoot Admin    | 监控框架         | 2.7.x    |
| Druid               | 数据库连接池     | 1.2.x    |
| MyBatis             | ORM框架          | 3.5.x    |
| MyBatis-Plus        | ORM框架          | 3.1.x    |
| Validation          | 数据校验         | 1.2.x    |
| PageHelper          | 分页框架         | 1.4.x    |
| MyBatis Generator   | 代码生成         | xxx      |
| Lombok              | 对象构造器       | 1.18.x   |
| Oss                 | 对象存储         | 3.10.x   |
| POI                 | 文件生成         | 4.1.x    |
| Knife4j             | API文档          | 4.3.x    |
| JJWT                | 登录校验/验证    | 0.9.x    |
| FastJson            | Json解析器       | 2.0.x    |

# 前端技术栈

| 依赖    | 作用                                    | 版本   |
| ------- | --------------------------------------- | ------ |
| Vue     | 前端开发框架                            | 2.6.x  |
| Axios   | 基于 promise 的 HTTP 库                 | 0.21.0 |
| Element | 基于MVVM框架Vue开源出来的一套前端ui组件 | 2.14.x |
| npm     | node.js的包管理工具                     | xxx    |

- vue-cli：Vue的脚手架工具，用于自动生成Vue项目的目录及文件。
- vue-router： Vue提供的前端路由工具，利用其我们实现页面的路由控制，局部刷新及按需加载，构建单页应用，实现前后端分离。
- vuex：Vue提供的状态管理工具，用于统一管理我们项目中各种数据的交互和重用，存储我们需要用到数据对象。

# 服务部署表格

| 服务名称 | 英文名           | 端口号 | 版本号 |
| -------- | ---------------- | ------ | ------ |
| 数据库   | mysql            | 3306   | 8.0+   |
| 缓存     | redis            | 6379   | 6.0+   |
| 注册中心 | nacos            | 8848   | 2.2.0  |
| 限流     | sentinel         | 8718   | 1.8.0  |
| 网关服务 | service-gateway  | 8080   | Java8+ |
| 认证服务 | service-auth     | 9200   | Java8+ |
| 系统服务 | service-system   | 9201   | Java8+ |
| 业务服务 | service-core     | 9501   | Java8+ |
| 功能服务 | service-function | 9202   | Java8+ |
| 监控服务 | service-monitor  | 9100   | Java8+ |


# 页面展示（todo）🌟

