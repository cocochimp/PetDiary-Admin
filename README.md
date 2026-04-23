# 宠物社区小程序

🎉基于SpringCloud Alibaba（gateway/nacos）、MyBatis-Plus、Redis、Druid、Knife4j、Vue、ElementUI、Ant Design mini微服务架构的宠物短视频社区小程序🐱🐶

github地址：[https://github.com/cocochimp/PetDiary-Admin](https://github.com/cocochimp/PetDiary-Admin)

# 项目介绍

主要有“后台管理系统”端和“微信小程序”端，在代码项目上主要分为三个部分：

* Java后端：采用SpringCloud Alibaba、MyBatis-Plus、MySQL、Redis等技术

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/ca1505c08b35d480ccd96ec0f8cb595.png)

* H5前端：采用Vue+ElementUI框架技术

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/762a2669b912928d4dd36cb5ad822e7.png)

* wx小程序：采用Ant Design mini（alibaba）框架技术

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/ca606ba70d0400028a56023b0809414.png)



# 部署方式

## Java后端部署

> 运行环境

~~~
Java>1.8
mysql>8.0
redis>6.0
nacos>2.0
sentinel>1.8（非必需）
~~~

* redis：直接运行本地的redis-server.exe

* nacos：打开conf文件夹下的application.properties文件，配置数据源

![image-20240323201708535](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/f5cd78a250d52ecb35eda7fb3e7421b.png)

启动nacos文件夹下的bin下的startup.cmd

~~~bash
startup.cmd -m standalone (win)
./startup.sh -m standalone (mac)
~~~

* sentinel：（非必要，流量控制/熔断降级）

~~~bash
java -jar sentinel-dashboard-1.8.0.jar --server.port=8718
~~~



## H5前端部署

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



# 项目结构

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



# 📚wxis页面展示📚

## “主页”模块

### 广场页/关注页

广场内容列表页面主要有“推荐”、“最新”、“图文”、“视频”、“养猫”以及“养狗”6种展示模式，在内容详情中，如果该内容为“视频”，则右上角会显示一个三角形符号，如果该内容为“图文”，则右上角会显示一个数量，该数量是内容详情的图片数量。

* **内容列表：**内容的“标题”、“标签种类”、“发布人”、“点赞数”以及“发布时间”5个部分，其中“发布人”和“标签种类”都可以进行点击，进入到相应的内容。
* **内容详情：**相比于列表，内部多增加了“点赞”、“关注”以及“评论”的功能

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/64c3ff604bcb5660928e5f48657849e.png)



### 热榜页

热榜页在“广场页/关注页”的基础上添加了一个“热度榜单”，其通过内容的数量对“宠物标签种类”进行热榜排序，具体形式是：

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/2e844f83d57510129b4f2855690288b.png)



## “商城”模块

### 商城列表

商城列表页面主要有“秒杀”和“热销”2种展示模式，相比于下面“热销列表”，上面“秒杀列表”的商品更加优惠，打折力度更大，但是每天秒杀的产品不同，上方有秒杀时间倒计时提示

* **商品列表：**内容的“图片/标题”、“商品种类”、“原价”以及“现价”4个部分。
* **商品详情：**相比于列表，内部增加了“图文详情”、“加入购物车”、“立即购买”以及“分享/客服”4个部分功能

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/683adba13b3bc4e9221e03be0f8ca39.png)



### 购物车/购买

进入商品详情页面后，用户可以点击“购物车”和“立刻购买”两个逻辑页面：

* **加入购物车：**点击加入购物车后默认将商品加入购物车，用户可以点击“我的”->"购物车"查看并管理购物车商品
* **立即购买：**用户可以填写订单，然后点击购买，购买成功后商品会进入“待发货列表”

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/d05382bbc86ef6da8ce433c76702261.png)



## “社区”模块

社区模块主要分为两大类功能模块：“功能区”和“宠物分类区”：

* **功能区：**在功能区中，现在的功能暂定只有“宠物识别”模块，用户可以通过拍照或者手动上传照片进行宠物种类的识别，结果会返回“宠物姓名”、“详细信息”以及“百度百科连接”3个部分
* **宠物分类区：**宠物分类区主要通过“宠物的大类”+“宠物的原生国”两个维度进行分类筛选，用户可以点击特定的宠物列表进入到特定的“宠物种类详情”中，其中，宠物种类详情分为上面**宠物介绍栏目**以及下面的**内容社区栏目**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/8940a9d514716e50044ad03f5a4e3ba.png)



## “我的”模块

我的模块中主要分为四大板块，从上到下依次是：个人信息、粉丝/关注列表、订单管理以及个人的内容管理。

* **个人信息：**用户点击个人信息可以“修改个人信息”
* **粉丝/关注列表：**用户点击相对应的粉丝或关注icon可以进入到列表，可以查看详细的关注列表信息
* **订单管理：**有“购物车”、“待发货”、“待收货”以及“全部订单”4个界面
* **内容管理：**内容管理主要分为“图片”和“视频”的内容管理，其排序为时间倒序

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/1417b7d7c2854e6937ee0d829ea5c99.png)





# 📚H5展示📚

## 登录页/首页

* **登录页**（详情账号密码看“sys_user数据库表”或者“系统管理->用户管理”）

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/55ea8c8a52848d64a8b1791140917a3.png)

* **首页：**该模块有各种e-chart图表，详情看下面内容

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/25c0201b6fb831b997634b3a6212a4d.png)

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/3f65650ecbc16cddd4f20d1439b69de.png)



## 系统管理

* **用户管理**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/01bacb47836a693e181de6b740d81a9.png)

* **角色管理**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/8aa677fe1aa4ac036b230f868d172da.png)

* **菜单管理**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/d4c75f4ec9fc45aae6a3bc535c7a8ad.png)

* **字典管理**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/de39c8f76a8c92a9d01b69a983a4063.png)

* **参数设置**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/851b46cea6d0e888c66fa4fafdf4729.png)



## 服务管理

* **在线用户**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/7cfb89d034a787cc3a069bef06f9982.png)

* **日志系统**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/469fc1a9ac79484c8a2c1eac7987ca9.png)

* **定时任务**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/babbeced6240f9ed607218f39ff9091.png)



## 内容管理

* **用户列表**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/9d1deb585011f4f9c9831a42a37526a.png)

* **图文/视频**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/10283afc924fecd8fecc5eb428f7027.png)

* **评论管理**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/2ac6c6fa2f23f0fd6d5760cd177d857.png)

* **宠物类别**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/d8397dc41dcabd91f2af69c238876a0.png)

* **通知公告**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/670ebf0c65cb411bbe9a44902172053.png)



## 商城管理

//todo待定



## 其他控制台

* **swagger文档**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/dfa8ce52a965207cfc027b54c5074e9.png)

* **Nacos控制台**

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/ef015c99e9644d1352cbca05ed80f82.png)

* **Admin控制台：**
  账号密码：root/123456

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/07825428e588e9aaacb1b8f80234232.png)

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/2be17ace6a40362cc0e3dfaf2aee92f.png)

* **Sentinel控制台：**

  账号密码：Sentinel/Sentinel

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/752eb8ecad6f70e3cb6da5cc0548804.png)

![aaa](https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/1f4f7faa73316a7a01353c64e511a9d.png)





# 🐳待改进问题🐳

//todo



