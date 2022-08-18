package com.barley.book.springboot.web;

import com.barley.book.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
IndexController
- URL 매핑 컨트롤러
- Mustache 파일(index.mustache)에 URL을 매핑

*/

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    /*
    머스테치 스타터
    - 컨트롤러에서 문자열 반환 시 앞의 경로, 뒤의 파일 확장자는 자동으로 저장

    index()
    - "index"를 반환 : src/main/resources/templates/index.mustache로 전환, View Resolver가 처리
      (templates/까지, 그리고 뒤의 .mustache가 알아서 붙음)
    - Model
        - 서버 템플릿 엔진에서 사용할 수 있는 객체 저장
        - 여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달

    */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }


    /*
    postsSave()
    - <a> 태그를 이용해 글 등록 페이지로 이동하는 글 등록 버튼 관련 컨트롤러
    - /posts/save 호출 시 posts-save.mustache 호출
    */
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
