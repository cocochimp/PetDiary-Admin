package com.ruoyi.function;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 功能模块
 * 
 * @author ruoyi
 */
//@EnableAdminServer
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients   
@SpringBootApplication
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class }) //文件上传
public class FunctionApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(FunctionApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  功能模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
