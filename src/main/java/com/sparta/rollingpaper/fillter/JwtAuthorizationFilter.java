package com.sparta.rollingpaper.fillter;

import com.sparta.rollingpaper.jwt.JwtUtil;
import com.sparta.rollingpaper.secuity.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;

@Slf4j(topic = "인가 확인")
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        String path = req.getRequestURI();

        // 로그인, 회원가입, 및 /api/rollingpapers/** 요청 시 필터 적용 건너뛰기
        if (path.equals("/api/login") || path.equals("/api/signup") || path.startsWith("/api/rollingpapers/")) {
            filterChain.doFilter(req, res);
            return;
        }


        try {
            String token = req.getHeader(JwtUtil.ACCESSTOKEN_HEADER);
            if (token == null) {
                throw new AuthenticationCredentialsNotFoundException("토큰이 없습니다.");
            }

            token = URLDecoder.decode(token, "UTF-8");
            if (StringUtils.hasText(token)) {
                String tokenValue = jwtUtil.substringToken(token);
                jwtUtil.validateToken(tokenValue);
                Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
                setAuthentication(info.getSubject());
            }
        } catch (AuthenticationCredentialsNotFoundException e) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 정보가 없습니다.");
            return;
        } catch (JwtException e) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
            return;
        } catch (Exception e) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
            return;
        }

        filterChain.doFilter(req, res);
    }


        // 인증 처리
    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = null;
        try {
            authentication = createAuthentication(email);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
