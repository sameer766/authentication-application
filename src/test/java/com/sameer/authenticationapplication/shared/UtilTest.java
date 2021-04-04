package com.sameer.authenticationapplication.shared;

import static com.sameer.authenticationapplication.shared.Utils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.sameer.authenticationapplication.response.UserDetailsResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UtilTest {


  @BeforeEach
  public void setUp() throws Exception {

  }


  @Test
  void testGenerateUserId() {
    final String userId = generateUserId(30);
    final String userId2 = generateUserId(30);
    assertNotNull(userId);
    assertEquals(userId.length(), 30);
    assertNotEquals(userId, userId2);
  }

  @Test
  void testTokenNotExpired() {
    final String token = generateEmailVerificationToken("samsbhdb");
    assertNotNull(token);
    boolean tokenExpired = hasTokenExpired(token);
    assertFalse(tokenExpired);
  }
//  @Test
//  void testTokenExpired()
//  {
//    final String expiredToken ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYW1zYmhkYiIsImV4cCI6MTYxNDI4MjQ3OX0.OPNKJuE5-uCrcvXrnopy0EcBDlF-dF6lLnWb2Bdx-L8CjUC4zPV1qfMAVIX_atWUFydiQl5__D6qfUA6ICupg";
//    boolean tokenExpired = hasTokenExpired(expiredToken);
//    assertTrue(tokenExpired);
//  }



}
