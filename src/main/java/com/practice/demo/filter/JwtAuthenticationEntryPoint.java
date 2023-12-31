package com.practice.demo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.demo.dto.common.CommonResponse;
import com.practice.demo.dto.common.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        returnUnauthorizedResponse(response);
    }

    private final ObjectMapper objectMapper;


    private void returnUnauthorizedResponse(HttpServletResponse response) throws IOException {
        CommonResponse<?> customResponse =
                new CommonResponse<>(StatusCode.InvalidToken.getValue(),
                        "Unauthorized - Invalid token");
        String jsonResponse = objectMapper.writeValueAsString(customResponse);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}
