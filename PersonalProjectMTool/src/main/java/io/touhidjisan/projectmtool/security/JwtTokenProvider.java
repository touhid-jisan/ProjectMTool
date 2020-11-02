package io.touhidjisan.projectmtool.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.touhidjisan.projectmtool.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import static io.touhidjisan.projectmtool.security.SecurityConstants.SECRET;
import static io.touhidjisan.projectmtool.security.SecurityConstants.EXPIRATION_TIME;

@Component
public class JwtTokenProvider {

    // generate token
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime()+EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", (user.getUsername()));
        claims.put("fullName", (user.getFullName()));

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

    }

    // validate the token


    // get user id from token


}
