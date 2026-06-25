//package cmms.Production.security;
//
//import cmms.Production.utils.AuditContextHolder;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import java.io.IOException;
//
//@Component
//@Slf4j
//public class ProductionAuditFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//
//        String userIdHeader = httpRequest.getHeader("X-Authenticated-User-Id");
//
//        log.info("<==================================>");
//        log.info(userIdHeader);
//
//        if (userIdHeader != null) {
//            try {
//                Long userId = Long.parseLong(userIdHeader.trim());
//                AuditContextHolder.setCurrentUserId(userId);
//            } catch (NumberFormatException e) {
//            }
//        }
//
//        try {
//            chain.doFilter(request, response);
//        } finally {
//            AuditContextHolder.clear();
//        }
//    }
//}


package cmms.Production.security;

import cmms.Production.utils.AuditContextHolder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.crypto.SecretKey;

@Slf4j
public class ProductionAuditFilter implements Filter {

    @Value("${app.jwt.secret}")
    private String jwtSecret ;


    @Value("${app.jwt.cookie-name}")
    private String cookieName;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        log.info("<===================== log.info==>");
        log.info(jwtSecret);

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String jwtToken = null;

        Cookie[] cookies = httpRequest.getCookies();
        log.info( Arrays.toString(cookies));
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        if (jwtToken != null) {
            try {

                SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(jwtToken)
                        .getPayload();

                Object idClaim = claims.get("id");

                if (idClaim != null) {
                    Long userId;
                    if (idClaim instanceof Number) {
                        userId = ((Number) idClaim).longValue();
                    } else {
                        userId = Long.parseLong(idClaim.toString().trim());
                    }

                    log.info(" Cookie Processed Successfully! Extracted User ID: {}", userId);

                    AuditContextHolder.setCurrentUserId(userId);
                }
            } catch (Exception e) {
                log.error(" Failed to parse or validate browser JWT cookie: {}", e.getMessage());
            }
        } else {
            log.warn("Request reached Production without cookie: [{}]", cookieName);
        }

        try {
            chain.doFilter(request, response);
        } finally {
            AuditContextHolder.clear();
        }
    }
}


