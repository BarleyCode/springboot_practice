package com.barley.book.springboot.web.dto;

/*
HelloController 코드 → Lombok으로 전환
- 롬복으로 변경한 후 문제가 생겼는지 알고 싶으면 테스트 코드만 돌리면 된다.
*/


/*
@Getter
- 선언된 모든 필드의 get 메소드 생성

@RequiredArgsConstructor
- 선언된 모든 final 필드가 포함된 생성자를 생성
- final이 없는 필드는 생성자에 포함되지 않음
*/
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;

}
