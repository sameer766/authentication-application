package com.sameer.authenticationapplication.security;

import com.sameer.authenticationapplication.SpringApplicationContext;

public class SecurityConstants {
  public static final long EXPIRATION_TIME = 86400000;
  public static final long PASSWORD_RESET_EXPIRATION_TIME = 1000*60*60;
  public static final String TOKEN_PREFIX = "BEARER";
  public static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/users";
  public static final String VERIFICATION_URL= "/users/email-verification";
  public static final String PASSWORD_RESET_URL = "/users/password-reset";
  public static final String RESET_PASSWORD = "/users/reset-password";

  public static String getTokenSecret() {
    AppProperties properties = (AppProperties) SpringApplicationContext.getBean("appProperties");
    return properties.getTokenSecret();
  }

}
