package com.barley.book.springboot.config.auth;
import com.barley.book.springboot.config.auth.dto.SessionUser;
import com.barley.book.springboot.domain.user.User;
import com.barley.book.springboot.domain.user.UserRepository;
import com.barley.book.springboot.config.auth.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    /*
    loadUser

    getRegistrationId()
    - 현재 로그인 진행 중인 서비스를 구분하는 코드
    - 지금은 구글만 사용하는 불필요한 값이나 이후 네이버 등 타 플랫폼 연동 시 플랫폼 구분 가능

    userNameAttributeName()
    - OAuth2 로그인 진행 시 키가 되는 필드값 이야기 (=Primary Key)
    - 구글의 경우 기본적으로 코드 지원. 네이버/카카오 등은 미지원
      (구글의 기본 코드 : sub)
    - 이후 네이버 로그인, 구글 로그인 동시 지원 시 사용

    OAuthAttributes
    - OAuth2UserService를 통해 가져온 OAuth2User의 Attribute를 담을 클래스
    - 네이버 등 다른 소셜 로그인도 이 클래스 사용

    SessionUser
    - 세션에서 사용자 정보를 저장하기 위한 Dto 클래스
    - User 클래스를 쓰지 않고 새로 만들어서 쓴다 (SessionUser 클래스 참조)
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());


    }
        /*
        saveOrUpdate
        - 사용자의 이름, 프로필 사진 변경 → User 엔티티에도 반영

        */

        private User saveOrUpdate(OAuthAttributes attributes) {
            User user = userRepository.findByEmail(attributes.getEmail())
                    .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                    .orElse(attributes.toEntity());
            return userRepository.save(user);
        }


}


