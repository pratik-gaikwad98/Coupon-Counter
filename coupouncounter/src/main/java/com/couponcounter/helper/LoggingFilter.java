package com.couponcounter.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.couponcounter.security.JwtRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);
        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());
        String reqUri = request.getRequestURI();

        if (reqUri.contains(".css") || reqUri.contains(".ico") || reqUri.contains(".jpg") || reqUri.contains(".mp4")) {

        } else if (!(requestBody.contains("<html>") || requestBody.contains("@extend")
                || responseBody.contains("</html>") || responseBody.contains("@extend")
                || responseBody.contains("@charse") || responseBody.contains("ÿØÿá")
                || responseBody.contains("é?ÂÒ£"))) {
            LOGGER.info(
                    "METHOD={}  | API={} | REQUEST={} | USER_ID ={}  |REQUEST_ID={} | CLIENT TYPE={} | CLIENT TIMESTAMP={}  | STATUS CODE={} | RESPONSE={} ",
                    request.getMethod(), request.getRequestURI(), requestBody, auth.getName(),
                    JwtRequestFilter.REQUEST_ID.get(), JwtRequestFilter.CLIENT_TYPE.get(),
                    java.time.LocalDateTime.now(), response.getStatus(), responseBody);

        } else {
            LOGGER.info(
                    "METHOD={}  | API={} | USER_ID ={}  | REQUEST_ID={} | CLIENT TYPE={} | CLIENT TIMESTAMP={}  | STATUS CODE={}  ",
                    request.getMethod(), request.getRequestURI(), auth.getName(), JwtRequestFilter.REQUEST_ID.get(),
                    JwtRequestFilter.CLIENT_TYPE.get(), java.time.LocalDateTime.now(), response.getStatus());
        }
        responseWrapper.copyBodyToResponse();

    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding)
            throws UnsupportedEncodingException {
        return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
    }

}