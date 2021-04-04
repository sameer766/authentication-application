package com.sameer.authenticationapplication;


import static com.sameer.authenticationapplication.shared.Utils.generateEmailVerificationToken;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.sameer.authenticationapplication.entity.AddressEntity;
import com.sameer.authenticationapplication.entity.UserEntity;
import com.sameer.authenticationapplication.exceptions.UserServiceException;
import com.sameer.authenticationapplication.repository.UserRepository;
import com.sameer.authenticationapplication.service.SmsService;
import com.sameer.authenticationapplication.service.UserServiceImpl;
import com.sameer.authenticationapplication.shared.AddressDto;
import com.sameer.authenticationapplication.shared.UserDto;
import com.sameer.authenticationapplication.shared.Utils;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {
  @InjectMocks
  UserServiceImpl userService;

  @Mock
  UserRepository userRepository;
  @Mock
  BCryptPasswordEncoder bCryptPasswordEncoder;
  @Mock
  UserDto userDto;
  @Mock
  SmsService smsService;
  @Mock
  Utils utils;
  UserEntity userEntity = null;

  @BeforeEach
  void setup() {
    MockitoAnnotations.initMocks(this);
    userEntity = UserEntity.builder()
        .id(1l)
        .firstName("Sameer")
        .email("s@ss.com")
        .emailVerificationStatus(Boolean.FALSE)
        .emailVerificationToken("sometoken")
        .addresses(getAddressEntities())
        .build();
    when(userRepository.findUserByEmail("s@ss.com")).thenReturn(userEntity);
  }

  @Test
  final void getUserImplTest() {
    assertNotNull(userService.getUser("s@ss.com"));
    assertEquals(userEntity.getFirstName(), "Sameer");
  }

  @Test
  final void getUserImplTest2() {
    when(userRepository.findUserByEmail("s@ss.com")).thenReturn(null);
    assertThrows(UserServiceException.class, () -> {
      userService.getUser("s@ss.com");
    });
  }

  void createUserImplTest() {
    when(bCryptPasswordEncoder.encode("passwordff")).thenReturn("748dndjbj");
    //when(utils.generateUserId(30)).thenReturn("ncsjnjcns jncjn");
    when(userRepository.save(any())).thenReturn(userEntity);
    when(generateEmailVerificationToken("1l")).thenReturn("sometoken");

    userDto = UserDto.builder()
        .id(1l)
        .firstName("Sameer")
        .userId("1l")
        .password("passwordff")
        .email("s@s.com")
        .emailVerificationStatus(Boolean.FALSE)
        .emailVerificationToken("sometoken")
        .addresses(getAddressDtos())
        .build();

    UserDto stored = userService.createUser(userDto);
    assertNotNull(stored);

    assertEquals(userDto.getFirstName(), stored.getFirstName());
    assertNotNull(userDto.getUserId());
    assertEquals(userDto.getAddresses().size(), stored.getAddresses().size());
    Mockito.doNothing().when(smsService).verifyEmail(userDto);
    verify(bCryptPasswordEncoder, times(1)).encode("passwordff");
  }

  private List<AddressEntity> getAddressEntities() {

    Type listType = new TypeToken<List<AddressEntity>>() {
    }.getType();
    return new ModelMapper().map(getAddressDtos(), listType);
  }

  private UserDto getUserDto() {
    Type listType = new TypeToken<List<UserDto>>() {
    }.getType();
    return new ModelMapper().map(userEntity, listType);
  }

  public List<AddressDto> getAddressDtos() {
    List<AddressDto> addressDtoList = Arrays.asList(new AddressDto(1l,
                                                                   "address1",
                                                                   "city1",
                                                                   "country1",
                                                                   "street1",
                                                                   "postalcode1",
                                                                   "shipping",
                                                                   userDto),
                                                    new AddressDto(2l,
                                                                   "address2",
                                                                   "city2",
                                                                   "country2",
                                                                   "street2",
                                                                   "postalcode2",
                                                                   "billing",
                                                                   userDto));
    return addressDtoList;
  }

}

