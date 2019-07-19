package com.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.entiy.User;

public class JwtUtils {

    public static String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
