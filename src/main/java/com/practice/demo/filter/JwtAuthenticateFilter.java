package com.practice.demo.filter;

import com.practice.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticateFilter extends OncePerRequestFilter {

    @Value("#{'${demo.ignore-jwt.urls}'.split(',')}")
    private List<String> ignoreJwtPaths;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        String path = request.getRequestURI();

        if (!isIgnoreJwtPath(path)) {
            if (token != null && JwtUtil.validateToken(token)) {
                Authentication authentication = JwtUtil.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized - Invalid token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtUtil.TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(JwtUtil.TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isIgnoreJwtPath(String path){
        return ignoreJwtPaths.contains(path);

    }
}

