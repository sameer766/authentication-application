package com.sameer.authenticationapplication.security;

import static com.sameer.authenticationapplication.security.SecurityConstants.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sameer.authenticationapplication.SpringApplicationContext;
import com.sameer.authenticationapplication.request.UserRequestLoginModel;
import com.sameer.authenticationapplication.service.UserService;
import com.sameer.authenticationapplication.shared.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;

  public AuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response)
      throws AuthenticationException {
    try {
      UserRequestLoginModel userRequestLoginModel = new ObjectMapper().readValue(request.getInputStream(),
                                                                                 UserRequestLoginModel.class);

      return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          userRequestLoginModel.getEmail(),
          userRequestLoginModel.getPassword(),
          new ArrayList<>()));


    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult)
  {
    String userName = ((User) authResult.getPrincipal()).getUsername();
    String token = Jwts.builder().
        setSubject(userName)
        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, getTokenSecret())
        .compact();
    UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
    UserDto userDto = userService.getUser(userName);
    response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    response.addHeader("UserId", userDto.getUserId());
  }
}
