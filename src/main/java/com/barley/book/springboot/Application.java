package com.barley.book.springboot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
@SpringBootApplication : 스프링부트의 자동 설정, Bean 읽기, 생성 모두 자동으로 설정
- @SpringBootApplication이 있는 위치부터 설정을 읽는다 → 반드시 최상단에 위치
*/

@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
