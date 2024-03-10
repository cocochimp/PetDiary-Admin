# PetDiary-Admin宠物社区（开发中）

🎉基于SpringCloud Alibaba（gateway/nacos）、MyBatis-Plus、Redis、Druid、Knife4j、Vue、ElementUI、Uniapp微服务架构的宠物短视频社区小程序（ruoyi）🐱🐶

github地址：[https://github.com/cocochimp/PetDiary-Admin](https://github.com/cocochimp/PetDiary-Admin)



# 启动命令

> 运行方式

1. 后端项目：

* redis：启动redis-server.exe

* nacos：启动nacos文件夹下的bin下的startup.cmd（记得配置mysql数据源，并且设置单机部署standalone）

~~~bash
./startup.sh -m standalone
~~~

* sentinel：（非必要，流量控制/熔断降级）

~~~bash
java -jar sentinel-dashboard-1.8.0.jar --server.port=8718
~~~

2. 前端项目：

~~~bash
# 安装依赖
npm install
npm install --registry=https://registry.npmmirror.com # npm 高速下载

# 启动服务
npm run dev

# 发布
npm run build:stage # 构建测试环境
npm run build:prod # 构建生产环境
~~~



> 运行环境

注：以下的启动命令是mac端的，window/linux自行查阅

- Java8+
- mysql8.0+
- redis6.0+（低版本适配）
- nacos2.0+
- sentinel1.8+（非必需）



# 项目结构

> 分布式服务

![image-20240310134331199](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-02/e6686e5f23662e2c18d8751debf3a8e.png)

| 服务名称 | 英文名           | 端口号 |
| -------- | ---------------- | ------ |
| 网关服务 | service-gateway  | 8080   |
| 认证服务 | service-auth     | 9200   |
| 系统服务 | service-system   | 9201   |
| 业务服务 | service-core     | 9501   |
| 功能服务 | service-function | 9202   |
| 监控服务 | service-monitor  | 9100   |



> 项目框架

```xml
PetDiary-Admin
├── service-api                         // API接口模块
├── service-common                      // 通用模块
       └── common-system                       // 核心模块
       └── common-redis                        // 缓存服务
       └── common-swagger                      // 系统接口
       └── common-security                     // 安全模块
├── service-center                      // 服务模块
       └── service-auth                        // 认证模块 [9200]
       └── service-core                        // 业务模块 [9501]
       └── service-function                    // 功能模块 [9202]
       └── service-gateway                     // 网关模块 [8080]
       └── service-monitor                     // 监控模块 [9100]
       └── service-system                      // 系统模块 [9201]
├──pom.xml
```

- 注：前端的端口为80



## 后端技术栈

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



## 前端技术栈

| 依赖    | 作用                                    | 版本   |
| ------- | --------------------------------------- | ------ |
| Vue     | 前端开发框架                            | 2.6.x  |
| Axios   | 基于 promise 的 HTTP 库                 | 0.21.0 |
| Element | 基于MVVM框架Vue开源出来的一套前端ui组件 | 2.14.x |
| npm     | node.js的包管理工具                     | xxx    |

- vue-cli：Vue的脚手架工具，用于自动生成Vue项目的目录及文件。
- vue-router： Vue提供的前端路由工具，利用其我们实现页面的路由控制，局部刷新及按需加载，构建单页应用，实现前后端分离。
- vuex：Vue提供的状态管理工具，用于统一管理我们项目中各种数据的交互和重用，存储我们需要用到数据对象。



## 服务部署表格

| 服务名称 | 英文名   | 端口号 | 版本号 |
| -------- | -------- | ------ | ------ |
| 数据库   | mysql    | 3306   | 8.0+   |
| 缓存     | redis    | 6379   | 6.0+   |
| 注册中心 | nacos    | 8848   | 2.2.0  |
| 限流     | sentinel | 8718   | 1.8.0  |





# 📚页面展示📚（todo）