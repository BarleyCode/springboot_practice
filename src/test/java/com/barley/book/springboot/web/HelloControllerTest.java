package com.barley.book.springboot.web;
/*
Test Code
- 검증할 때는 테스트 코드로 먼저 검증.
- 테스트 코드로도 못 믿겠다 싶으면 그 때 수동으로 검증
    - Application.java에서 main 함수 실행 (초록색 화살표 누르기)

*/
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
RunWith
- 테스트 진행 시 JUnit에 내장된 실행자 외에 다른 실행자 실행
- 여기서는 SpringRunner라는 스프링 실행자 사용
- 즉, 스프링부트 테스트와 JUnit 사이에 연결자 역할

WebMvcTest
- Web(Spring MVC)에 집중
- 선언 시
    - 사용 가능 - @Controller, @ControllerAdvice
    - 사용 불가 - @Service, @Component, @Repository
- 여기서는 컨트롤러만 사용하기 때문에 선언

AutoWired
- 스프링이 관리하는 Bean 주입받음
*/
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    /*
    private MockMvc mvc
    - 웹 API 테스트시 사용
    - 스프링 MVC 테스트의 시작점
    - 체이닝 지원 - 여러 검증 기능 이어서 선언 가능

    .andExpect(status().isOk())
    - mvc.perform의 결과 검증
    - HTTP Header의 status 검증
        - 200, 404, 500 등
    - 여기선 OK(200) 검증

    .andExpect(content().string(hello))
    - mvc.perform의 결과 검증
    - 응답 본문의 내용 검증
    - Controller에서 "hello" 리턴 → 이 값이 맞는지 검증

    */
    @Test
    public void hello_return () throws Exception {
        // 책에서의 함수 이름 : hello가_리턴된다()
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));

    }

    @Test
    public void helloDto_return() throws Exception {
        String name = "hello";
        int amount = 1000;

        /*
        .param()
        - API 테스트 시 사용될 요청 파라미터 설정
        - 단, 값은 String만 허용
        - 그래서 숫자/날짜 데이터도 등록할 때는 문자열로 변경 필요

        .jsonPath()
        - JSON 응답값을 필드별로 검증할 수 있는 메소드
        - $을 기준으로 필드명을 명시
        - 여기서는 name, amount를 검증하므로 $.name같은 형태로 입력

        */

        mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));

    }
}
