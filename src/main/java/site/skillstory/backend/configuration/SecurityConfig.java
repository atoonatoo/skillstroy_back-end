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
                                .requestMatchers("/api/users/**").permitAll()
                                .requestMatchers("/static/**").permitAll()
                                .requestMatchers("/log.png").permitAll()
                                .requestMatchers("/api/posts/create").authenticated()
                                .anyRequest().authenticated())
                .formLogin((form) ->
                        form
                                .usernameParameter("username") // 로그인 폼에서 사용자 이름을 입력하는 필드 설정
                                .passwordParameter("password") // 로그인 폼에서 비밀번호를 입력하는 필드 설정
                                .loginProcessingUrl("/api/users/auth") // 로그인을 처리하는 URL 설정 이 URL로 POST 요청을 보내면 로그인
                                .defaultSuccessUrl("/api/home", true)
                                .permitAll()
                                .successForwardUrl("/api/users/authSuccess") // 로그인이 성공했을 때 리다이렉트할 URL을 /api/user/authSuccess로 설정
                                .failureForwardUrl("/api/users/authFail")) // 로그인이 실패했을 때 리다이렉트할 URL을 /api/user/authFailure로 설정
                .logout((logout) -> // 로그아웃 처리에 대한 정의
                        logout
                                .logoutUrl("/api/users/logOut")
                                // 로그아웃 처리 url 설정, /api/user/logOut로 요청이 들어오면 로그아웃을 수행

                                .logoutSuccessUrl("/api/users/logOutSuccess")
                                // 로그아웃이 성공하면 사용자를 /api/user/logOutSuccess URL로 리다이렉트

                                .clearAuthentication(true)
                                // 로그아웃 시 사용자의 인증 정보를 제거, 이 옵션을 활성화하면 사용자가 더 이상 인증된 상태가 아님

                                .deleteCookies("JSESSIONID"))
                                // 로그아웃 후 세션 쿠키(JSESSIONID)를 삭제하여 사용자의 세션을 만료시킨다.
                                // 이는 사용자가 로그아웃된 후 세션을 재사용하지 못하도록 보장한다.

                                .userDetailsService(userDetailService);
                                // 사용자 인증 정보를 로드하는 서비스 설정을 지정
                                // CustomeUserDetailService를 사용하여 사용자의 인증 정보(예: 사용자 이름, 비밀번호, 권한)를 로드하도록 Spring Security가 처리한다.
                                // 이 서비스는 로그인 요청을 처리할 때 사용자의 인증 정보를 검증하는 데 사용된다.

        httpSecurity.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);
        // CORS 필터를 인증 필터 전에 추가한다.
        // CORS(Cross-Origin Resource Sharing) 필터를 UsernamePasswordAuthenticationFilter 이전에 추가하여 인증을 처리하기 전에
        // CORS 관련 규칙을 적용하도록 한다.
        // 이는 프론트엔드(예: React)와 백엔드(Spring Boot) 간의 요청이 다른 도메인 간에 이루어질 때 필요한 설정이다.
        // 예를 들어, 프론트엔드가 http://localhost:3000에서 요청을 보낼 때, 백엔드에서 이를 허용할 수 있도록 CORS 규칙을 설정한다.

        return httpSecurity.build();
        // 설정을 마친 HttpSecurity 객체를 반환하여 Spring Security가 이 설정을 적용하도록 합니다.
        // HttpSecurity 객체를 빌드하여 보안 설정을 최종적으로 적용하고, 이를 Spring Security가 사용합니다.

        // 1. CSRF(Cross-Site Request Forgery) 보호를 비활성화한다.

    }

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("http://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);


        // 1. CORS 필터를 구성하는 메서드이다.
        // CORS는 다른 도메인에서의 HTTP 요청을 허용하는 보안 기능이다.
        // 이 설정은 주로 프론트엔드(예: React)와 백엔드(Spring Boot)가 서로 다른 도메인에서 운영될 때 필요하다.

        // 2. 자격 증명(예: 쿠키, 인증 정보 등)을 허용한다.

        // 3. CORS 요청을 허용할 도메인을 지정합니다.
        // 여기서는 http://localhost:3000에서 오는 요청을 허용한다.

        // 4. 모든 헤더를 허용합니다.

        // 5. 모든 HTTP 메서드(GET, POST, PUT, DELETE 등)를 허용한다.

        // 6. CORS 설정을 URL 패턴에 기반하여 적용하는 객체이다.

        // 7. 특정 URL 경로에 대해 CORS 규칙을 설정할 수 있다.
        // 여기서는 모든 경로(/**)에 대해 위에서 정의한 CORS 규칙을 적용하도록 설정한다.

        // 8. 설정된 CORS 규칙을 필터로 반환한다.
        // 이 메서드는 최종적으로 CORS 필터 객체를 생성하고 반환하여, Spring Security가 이 필터를 적용하도록 한다.
        // 이를 통해 http://localhost:3000에서 백엔드로의 요청이 허용되고,
        // 프론트엔드와 백엔드 간의 CORS 문제가 발생하지 않도록 보장한다.
    }

}
