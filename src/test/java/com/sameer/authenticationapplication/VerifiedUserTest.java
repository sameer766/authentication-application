//package com.sameer.authenticationapplication;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//import com.sameer.authenticationapplication.entity.UserEntity;
//import com.sameer.authenticationapplication.repository.UserRepository;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class VerifiedUserTest {
//
//  @Autowired
//  UserRepository userRepository;
//
//  @BeforeEach
//  public void setUp() {
//
//  }
//
//  @Test
//  public void test() {
//    Pageable pageable = PageRequest.of(0, 2);
//    final Page<UserEntity> pages = userRepository.userWithEmailAddressVerified(pageable);
//    assertNotNull(pages);
//    List<UserEntity> userEntityList = pages.getContent();
//    System.out.println(userEntityList);
//    assertEquals(userEntityList.size(), 1);
//  }
//
//  @Test
//  public void test1() {
//    final List<UserEntity> user = userRepository.findUserByFirstName("raju");
//    assertNotNull(user);
//    assertEquals(user.size(), 1);
//  }
//
//  @Test
//  public void test2() {
//    final List<UserEntity> user = userRepository.findUserByLastName("kaju");
//    assertNotNull(user);
//    assertEquals(user.size(), 1);
//  }
//
//  @Test
//  public void test3() {
//    final List<UserEntity> user = userRepository.findUserContainingKeyword("dbc", "aju");
//    assertNotNull(user);
//    assertEquals(user.size(), 1);
//    assertEquals(user.get(0).getFirstName(), "raju");
//    assertEquals(user.get(0).getLastName(), "kaju");
//  }
//
//  @Test
//  public void test4() {
//    final List<Object[]> user = userRepository.findUserContainingKeywordOnlyName("dbc", "aju");
//    assertNotNull(user);
//    assertEquals(user.size(), 1);
//    assertEquals(user.get(0)[0], "raju");
//    assertEquals(user.get(0)[1], "kaju");
//  }
//
//  @Test
//  public void test5() {
//    userRepository.updateUserEmailVerificationStatus(0, "UBLLZEEBESYQKRUXEJTSRKNNBZDERR");
//    final UserEntity user = userRepository.findUserByUserId(
//        "UBLLZEEBESYQKRUXEJTSRKNNBZDERR");
//    assertEquals(user.getEmailVerificationStatus(),Boolean.FALSE);
//
//  }
//  @Test
//  public void test6() {
//    final UserEntity user = userRepository.findUserByFirstNameJpql("raju");
//    assertEquals(user.getFirstName(),"raju");
//
//  }
//
//
//}
