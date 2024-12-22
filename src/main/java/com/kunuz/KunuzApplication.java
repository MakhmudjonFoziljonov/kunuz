package com.kunuz;

import com.kunuz.util.RandomUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class KunuzApplication {

    public static void main(String[] args) {
        SpringApplication.run(KunuzApplication.class, args);
    }
}
