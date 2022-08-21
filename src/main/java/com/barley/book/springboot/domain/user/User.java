package com.barley.book.springboot.domain.user;
import com.barley.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    /*
    id
    - @Id 어노테이션이 있어야 한다. 없으면 클래스 자체에서 오류가 생긴다
      (Persistent Entity 'User' should have primary key)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    /*
    @Enumerated(EnumType.STRING)
    - JPA로 DB에 저장 시 Enum 값을 어떤 형태로 저장할 지 결정
    - 기본적으로는 int로 된 숫자가 적당
    - 숫자로 저장되면 DB로 확인할 때 그 값이 무슨 코드를 의미하는지 알 수가 없다.
    - 그래서 문자열 (EnumType.STRING)로 저장될 수 있도록 선언
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }



}
