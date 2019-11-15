package com.wanguliuwang.dota;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wanguliuwang.dota.Mapper")
public class DotaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DotaApplication.class, args);
    }

}
