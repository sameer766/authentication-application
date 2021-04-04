package com.sameer.authenticationapplication.service;

import com.sameer.authenticationapplication.shared.UserDto;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface  UserService extends UserDetailsService {
  UserDto  createUser(UserDto userDto);

  UserDto getUser(String email);

  UserDto getUserById(String userId);

  UserDto updateUser(String userId, UserDto userDto);

  void deleteUser(String userId);

  List<UserDto> getAllUsers(int page,int limit);

  boolean verifyEmailToken(String token);

  boolean requestPasswordReset(String email);

  boolean resetPassword(String token, String password);
}
