package com.practice.demo.filter;



import com.practice.demo.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.regex.Pattern;

@Component
public class JwtAuthenticateFilter extends OncePerRequestFilter {

    private final static Logger log = LoggerFactory.getLogger(JwtAuthenticateFilter.class);

    @Value("#{'${demo.ignore-jwt.open-api.urls}'.split(',')}")
    private List<String> ignoreJwtPaths;
    @Value("#{'${demo.ignore-jwt.open-api.patterns}'.split(',')}")
    private List<String> ignoreJwtPatterns;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        log.debug("jwt auth filter request path:{}", path);
        if (!isIgnoreJwtPath(path)) {
            String token = getTokenFromRequest(request);
            log.debug("visiting token :{}" ,token);
            if (token != null && JwtUtil.validateToken(token)) {
                Authentication authentication = JwtUtil.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
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
        return ignoreJwtPaths.contains(path) || isMatchPattern(path);

    }

    private boolean isMatchPattern(String path){
        return ignoreJwtPatterns.stream().map(Pattern::compile)
                .anyMatch(pattern  -> pattern.matcher(path).matches());
    }
}



