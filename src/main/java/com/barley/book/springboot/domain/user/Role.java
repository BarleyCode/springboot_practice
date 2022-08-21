package com.barley.book.springboot.domain.user;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/*
Role
- 스프링 시큐리티에서는 권한 코드에 항상 ROLE_ 이 앞에 있어야 한다.
- 그래서 GUEST, USER 앞에 ROLE_을 붙였다.
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
