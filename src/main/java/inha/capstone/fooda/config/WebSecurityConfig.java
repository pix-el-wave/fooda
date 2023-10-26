package inha.capstone.fooda.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import inha.capstone.fooda.domain.common.exception.ExceptionType;
import inha.capstone.fooda.domain.common.response.ErrorResponse;
import inha.capstone.fooda.security.JwtAuthenticationFilter;
import inha.capstone.fooda.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 사용
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/actuator/**"
    };
    private static final String BASE_URL = "/api";

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(c -> c.configurationSource(
                request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOriginPatterns(Collections.singletonList("*"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(List.of("Authorization"));
                    config.setMaxAge(3600L);
                    return config;
                }));
        http.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(c -> c
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers(BASE_URL + "/auth/local/new").permitAll()
                .requestMatchers(BASE_URL + "/auth/local").permitAll()
                .requestMatchers(BASE_URL + "/auth/kakao").permitAll()
                .requestMatchers(BASE_URL + "/auth/kakao/new").permitAll()
                .anyRequest().authenticated());
        http.addFilterBefore(
                new JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class
        );

        http.exceptionHandling((exception) -> exception
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    // 권한 문제가 발생했을 때 이 부분을 호출한다.
                    log.error("SecurityConfig.SecurityFilterChain.accessDeniedHandler() ex={}",
                            String.valueOf(accessDeniedException));

                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json; charset=UTF-8");

                    int errorCode = ExceptionType.ACCESS_DENIED_EXCEPTION.getErrorCode();
                    String errorMessage = ExceptionType.ACCESS_DENIED_EXCEPTION.getErrorMessage();
                    ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMessage);

                    response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                }).authenticationEntryPoint((request, response, authException) -> {
                    // 인증문제가 발생했을 때 이 부분을 호출한다.
                    log.error("SecurityConfig.SecurityFilterChain.authenticationEntryPoint() ex={}",
                            String.valueOf(authException));

                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json; charset=UTF-8");

                    int errorCode = ExceptionType.AUTHENTICATION_EXCEPTION.getErrorCode();
                    String errorMessage = ExceptionType.AUTHENTICATION_EXCEPTION.getErrorMessage();
                    ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMessage);

                    response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                }));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private final JwtTokenProvider jwtTokenProvider;
}