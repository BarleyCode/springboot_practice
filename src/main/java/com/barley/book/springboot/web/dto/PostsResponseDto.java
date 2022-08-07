package com.barley.book.springboot.web.dto;

import com.barley.book.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    /*
    PostsResponseDto
    - Entity를 받아서 처리. Entity 필드 중 일부만 사용
    - 굳이 모든 필드를 가진 생성자가 필요하지는 않다.

    */
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
