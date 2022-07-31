package com.barley.book.springboot.web;
// import com.barley.book.springboot.web.dto.HelloResponseDto;
import com.barley.book.springboot.web.dto.HelloResponseDto;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

/*
참고 : lombok 테스트 전 gradle 버전이 4가 아니라면
- https://github.com/jojoldu/freelec-springboot2-webservice/issues/78
- dependencies, configurations을 위와 같이 수정

*/
public class HelloResponseDtoTest {
    @Test
    public void lombok_test() {
        // 책에서의 함수 이름 : 롬복_기능_테스트()
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        /*
        assertThat
        - assertj라는 테스트 검증 라이브러리의 검증 메소드
        - 검증하고 싶은 대상을 메소드 인자로 받는다.
        - 메소드 체이닝 지원 → isEqualTo와 같이 메소드를 이어서 사용 가능

        isEqualTo
        - assertj의 동등 비교 메소드
        - assertThat에 있는 값과 isEqualTo의 값을 비교, 같을 때만 성공

        */
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
