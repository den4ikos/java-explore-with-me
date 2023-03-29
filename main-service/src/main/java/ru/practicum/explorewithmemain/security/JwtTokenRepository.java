package ru.practicum.explorewithmemain.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;

public class JwtTokenRepository implements CsrfTokenRepository {
    @Getter
    private String secret;

    @Value("${jwt.header.name}")
    private String headerName;

    @Value("${jwt.parameter.name}")
    private String parameterName;

    public JwtTokenRepository() {
        this.secret = "explore-with-me";
    }
    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusMinutes(60).atZone(ZoneId.systemDefault()).toInstant());

        String token = "";
        try {
            token = Jwts.builder()
                    .setId(id)
                    .setIssuedAt(now)
                    .setNotBefore(now)
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.ES256, secret)
                    .compact();
        } catch (JwtException e) {
            e.printStackTrace();
        }

        return new DefaultCsrfToken(headerName, parameterName, token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        if (Objects.nonNull(token)) {
            if (!response.getHeaderNames().contains(ACCESS_CONTROL_EXPOSE_HEADERS)) {
                response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, token.getHeaderName());
            }

            if (response.getHeaderNames().contains(token.getHeaderName())) {
                response.setHeader(token.getHeaderName(), token.getToken());
            } else {
                response.addHeader(token.getHeaderName(), token.getToken());
            }
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }

    public void clearToken(HttpServletResponse response) {
        if (response.getHeaderNames().contains(headerName)) {
            response.setHeader(headerName, "");
        }
    }
}
