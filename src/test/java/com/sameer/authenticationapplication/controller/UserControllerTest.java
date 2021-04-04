package com.sameer.authenticationapplication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.sameer.authenticationapplication.response.UserDetailsResponseModel;
import com.sameer.authenticationapplication.service.UserService;
import com.sameer.authenticationapplication.service.UserServiceImpl;
import com.sameer.authenticationapplication.shared.UserDto;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserControllerTest {
  UserDto userDto;

  @InjectMocks
  UserController userController;

  @Mock
  UserServiceImpl userService;

  @BeforeEach
  public void setUp() throws Exception {
    userDto = new UserDto().builder()
        .userId("1l")
        .id(1l)
        .email("pandesameer76")
        .encryptedPassword("xshbxhsb")
        .emailVerificationToken("bxhbhxbshbxh")
        .build();
    MockitoAnnotations.initMocks(this);
  }


  @Test
  void getUserTest() {
    when(userService.getUserById("1l")).thenReturn(userDto);
    final UserDetailsResponseModel user = userController.getUser("1l");
    assertNotNull(user);
    assertEquals(user.getFirstName(), userDto.getFirstName());
  }
}
