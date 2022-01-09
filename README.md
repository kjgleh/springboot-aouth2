# SpringBoot를 이용한 oauth2 로그인 구현

## build.gradle.kts
- Spring Security 설정 추가
```kotlin
implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
```

## application.yml
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: 구글 클라우드 콘솔에서 발급받은 client-id
            client-secret: 구글 클라우드 콘솔에서 발급받은 client-secret
            scope: profile,email
```
- https://console.cloud.google.com/ 에서 Credential(Client ID, Client secret)을 발급 받는다.
  - 발급 시 Authorized redirect URIs는 spring security oauth2에서 기본으로 제공하는 URI를 입력한다.
    - http://localhost:8080/login/oauth2/code/google
- scope
  - 기본값: openId, profile, email
  - openId가 scope에 포함되어 있으면 해당 리소스 서버는 openId Provider로 인식된다.
  - 네이버나 카카오는 openId Provider가 아니라서 리소스 서버가 openId Provider 여부에 따라 각각 OAuth2Service를 만들어야 한다.

## index.mustache
```html
<a href="/oauth2/authorization/google" class="btn btn-success active" role="button">Google Login</a>
```
/oauth2/authorization/google: 

## SecurityConfig
```kotlin
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var customOAuth2UserService: CustomOAuth2UserService

    override fun configure(http: HttpSecurity) { 
      ...
          .and()
          .oauth2Login() // OAuth2 로그인 기능에 대한 진입점
          .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
          .userService(customOAuth2UserService) // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체 등록 
      ...
    }
}
```
