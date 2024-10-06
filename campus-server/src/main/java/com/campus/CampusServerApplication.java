package com.campus;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan({"com.campus.*","com.utils"})
@EnableTransactionManagement //开启注解方式的事务管理
@ServletComponentScan
@SpringBootApplication
@MapperScan("com.campus.mapper")
public class CampusServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusServerApplication.class, args);
    }

}
