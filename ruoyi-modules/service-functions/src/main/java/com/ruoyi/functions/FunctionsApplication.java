package com.ruoyi.functions;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 功能模块
 * 
 * @author ruoyi
 */
@EnableAdminServer
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients   
@SpringBootApplication
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class }) //文件上传
public class FunctionsApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(FunctionsApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  功能模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
