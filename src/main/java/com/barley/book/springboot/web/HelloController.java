package com.barley.book.springboot.web;

import com.barley.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨트롤러 → JSON으로 반환
public class HelloController {

    @GetMapping("/hello")   // HTTP Method인 GET 요청 받는 API
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(
                @RequestParam("name") String name
                , @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
        /*
        RequestParam
        - 외부에서 API로 넘긴 파라미터를 가져오는 annotation
        - 여기서는 외부에서 name(@RequestParam("name")) 이란 이름으로 넘긴 파라미터를
          메소드 파라미터 name(String name)에 저장

        */
    }

}
