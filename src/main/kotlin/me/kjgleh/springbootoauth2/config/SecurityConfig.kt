package me.kjgleh.springbootoauth2.config

import me.kjgleh.springbootoauth2.domain.enums.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var customOAuth2UserService: CustomOAuth2UserService

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .headers().frameOptions().disable()

            .and()
            .authorizeRequests()
            .antMatchers(
                "/",
                "/css/**",
                "/images/**",
                "/js/**",
                "/h2-console/**"
            ).permitAll()
            .antMatchers("/api/v1/**").hasRole(Role.USER.name)
            .anyRequest().authenticated()

            .and()
            .logout()
            .logoutSuccessUrl("/")

            .and()
            .oauth2Login() // OAuth2 로그인 기능에 대한 진입점
            .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
            .userService(customOAuth2UserService) // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체 등록
    }
}
