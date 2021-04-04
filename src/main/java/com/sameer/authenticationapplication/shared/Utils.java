package com.sameer.authenticationapplication.shared;

import com.sameer.authenticationapplication.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class Utils {

  private static final Random random = new SecureRandom();
  private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static String generateUserId(int length) {
    return generateRandomId(length);
  }

  public static String generateAddressId(int length) {
    return generateRandomId(length);
  }

  private static String generateRandomId(int length) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      stringBuilder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));

    }
    return stringBuilder.toString();
  }


  public static boolean hasTokenExpired(String token) {
    boolean hasExpired;
    try {
      Claims claims = Jwts.parser().setSigningKey(SecurityConstants.getTokenSecret())
          .parseClaimsJws(token)
          .getBody();
      Date expirationDate = claims.getExpiration();
      Date today = new Date();

      hasExpired = expirationDate.before(today);
    } catch (ExpiredJwtException e) {
      hasExpired = true;
    }

    return hasExpired;
  }

  public static String generateEmailVerificationToken(String userId) {
    return Jwts.builder()
        .setSubject(userId)
        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
        .compact();
  }

  public static String generatePasswordResetToken(String userId) {
    return Jwts.builder()
        .setSubject(userId)
        .setExpiration(new Date(System.currentTimeMillis()
                                    + SecurityConstants.PASSWORD_RESET_EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
        .compact();
  }
}
