package site.skillstory.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import site.skillstory.backend.service.implement.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CustomUserDetailService userDetailService) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/api/users/**").permitAll()  // 회원가입 및 인증 관련 경로는 모두 허용
                                .requestMatchers("/static/**").permitAll()
                                .requestMatchers("/log.png").permitAll()
                                .requestMatchers("/api/posts/create").authenticated()  // 이 경로는 인증된 사용자만 접근 가능
                                .anyRequest().authenticated())  // 그 외 모든 경로는 인증 필요
                .formLogin((form) ->
                        form
                                .usernameParameter("username") // 로그인 폼에서 사용자 이름 필드 설정
                                .passwordParameter("password") // 로그인 폼에서 비밀번호 필드 설정
                                .loginProcessingUrl("/api/users/auth") // 로그인을 처리할 URL 설정 (POST)
                                .defaultSuccessUrl("/api/home", true)  // 로그인 성공 시 이동할 URL
                                .permitAll()
                                .successForwardUrl("/api/users/authSuccess") // 성공 시 추가 경로
                                .failureForwardUrl("/api/users/authFail"))  // 실패 시 리다이렉트
                .logout((logout) ->
                        logout
                                .logoutUrl("/api/users/logOut")  // 로그아웃 URL
                                .logoutSuccessUrl("/api/users/logOutSuccess")  // 로그아웃 성공 후 이동할 URL
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID"))  // 로그아웃 후 세션 삭제
                .userDetailsService(userDetailService);

        httpSecurity.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("http://localhost:3000");  // 허용된 도메인
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);
    }
}

