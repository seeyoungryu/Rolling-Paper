package com.sparta.rollingpaper.fillter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.rollingpaper.dto.LoginRequestDto;
import com.sparta.rollingpaper.jwt.JwtUtil;
import com.sparta.rollingpaper.secuity.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            // AuthenticationManager 가 인증처리
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUserId(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String userId = userDetails.getUsername(); // UserDetailsImpl에서 userId를 가져옴
        String userName = userDetails.getUser().getUserName(); // UserDetailsImpl에서 userName을 가져옴
        String role = "ROLE_USER"; // 모든 사용자에게 동일한 권한 부여

        String accessToken = jwtUtil.createAccessToken(userId, role);

        // JWT를 헤더에 추가
        jwtUtil.addJwtToHeader(JwtUtil.ACCESSTOKEN_HEADER, accessToken, response);

        // CORS 헤더를 설정
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // 응답 본문에 JSON 형태로 accessToken, userId, userName을 반환.
        Map<String, String> tokens = new HashMap<>();
//        tokens.put("accessToken", accessToken);
        tokens.put("userId", userId);
        tokens.put("userName", userName);
        response.getWriter().write(objectMapper.writeValueAsString(tokens));
        response.setStatus(HttpServletResponse.SC_OK);

//        response.setContentType("application/json;charset=UTF-8");
////        response.getWriter().write("{\"accessToken\": \"" + accessToken + "\"}"); // JSON 형태로 accessToken만 반환
//        // JSON 형태로 accessToken, userId, userName 반환
//        Map<String, String> tokens = new HashMap<>();
////        tokens.put("accessToken", accessToken);
//        tokens.put("userId", userId);
//        tokens.put("userName", userName);
//        response.getWriter().write(objectMapper.writeValueAsString(tokens));
//        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("ID 또는 비밀번호가 틀립니다")));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Getter
    @Setter
    private static class SimpleResponse {
        private String message;

        private SimpleResponse(String message) {
            this.message = message;
        }
    }
}
