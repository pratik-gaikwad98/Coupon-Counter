package com.couponcounter.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieUtil {
    @Value("${authentication-test.auth.accessTokenCookieName}")
    private String accessTokenCookieName;

    @Value("${authentication-test.auth.refreshTokenCookieName}")
    private String refreshTokenCookieName;

    public void addTokenCookie(HttpServletResponse response, String token, String cookieName) {
        token = SecurityCipher.encrypt(token);
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setMaxAge(180000);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public String readServletCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (accessTokenCookieName.equals(cookie.getName())) {
                String accessToken = cookie.getValue();
                if (accessToken == null) {
                    return null;
                }
                accessToken = SecurityCipher.decrypt(accessToken);
                return accessToken;
            }
        }
        return null;
    }

    public void deleteTokenCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}