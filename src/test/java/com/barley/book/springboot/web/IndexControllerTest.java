package com.barley.book.springboot.web;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


/*
IndexControllerTest
- 실제로 URL 호출 시 페이지의 내용이 제대로 호출되는지에 대한 테스트

*/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void mainPage_loading() {
        /*
        mainPage_loading() 원래 이름 : 메인페이지_로딩()
        - 실제로 확인 : Application.java 내 main 메소드 실행
        */
        // when
        String body = this.restTemplate.getForObject("/", String.class);

        // then
        // index.mustache에서 작성한 h1 내 문자열과 일치해야 한다.
        assertThat(body).contains("스프링부트로 시작하는 웹 서비스");

    }

}
