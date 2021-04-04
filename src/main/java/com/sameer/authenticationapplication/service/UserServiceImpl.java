package com.sameer.authenticationapplication.service;

import static com.sameer.authenticationapplication.shared.Utils.*;

import com.sameer.authenticationapplication.entity.PasswordResetTokenEntity;
import com.sameer.authenticationapplication.entity.UserEntity;
import com.sameer.authenticationapplication.exceptions.UserServiceException;
import com.sameer.authenticationapplication.repository.PasswordResetTokenRepository;
import com.sameer.authenticationapplication.repository.UserRepository;
import com.sameer.authenticationapplication.response.ErrorMessages;
import com.sameer.authenticationapplication.shared.AddressDto;
import com.sameer.authenticationapplication.shared.UserDto;
import com.sameer.authenticationapplication.shared.Utils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;
  @Autowired
  Utils utils;
  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  PasswordResetTokenRepository passwordResetTokenRepository;
  @Autowired
  SmsService smsService;

  @Override
  public UserDto createUser(UserDto userDto) {
    UserEntity userByEmail = userRepository.findUserByEmail(userDto.getEmail());
    if (userByEmail != null) {
      throw new RuntimeException("User email already exists");
    }
    for (int i = 0; i < userDto.getAddresses().size(); i++) {
      AddressDto addressDto = userDto.getAddresses().get(i);
      addressDto.setUserDto(userDto);
      addressDto.setAddressId(generateAddressId(30));
      userDto.getAddresses().set(i, addressDto);
    }
    ModelMapper modelMapper = new ModelMapper();
    UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);


    userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

    String userId = generateUserId(30);
    userEntity.setUserId(userId);
    userEntity.setEmailVerificationToken(generateEmailVerificationToken(userId));
    userEntity.setEmailVerificationStatus(Boolean.FALSE);
    UserEntity storedUser = userRepository.save(userEntity);
    log.info("User saved to the database");
    UserDto returnVal = modelMapper.map(storedUser, UserDto.class);

    smsService.verifyEmail(returnVal);
    log.info("Sending user email for verification");

    return returnVal;
  }

  @Override
  public UserDto getUser(String email) {
    UserEntity userEntity = userRepository.findUserByEmail(email);
    if (userEntity == null) {
      throw new UserServiceException(email + " Not found");
    }
    UserDto returnVal = new UserDto();
    BeanUtils.copyProperties(userEntity, returnVal);
    return returnVal;
  }

  @Override
  public UserDto getUserById(String userId) {
    UserDto returnVal = new UserDto();
    UserEntity userEntity = userRepository.findUserByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException("User not found for user Id " + userId);
    }
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.map(userEntity, returnVal);
    return returnVal;
  }

  @Override
  public UserDto updateUser(String userId, UserDto userDto) {

    UserEntity userEntity = userRepository.findUserByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }
    userEntity.setFirstName(userDto.getFirstName());
    userEntity.setLastName(userDto.getLastName());


    UserEntity updatedUser = userRepository.save(userEntity);
    return new ModelMapper().map(updatedUser, UserDto.class);
  }

  @Override
  public void deleteUser(String userId) {
    UserEntity userEntity = userRepository.findUserByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }
    userRepository.delete(userEntity);
  }

  @Override
  public List<UserDto> getAllUsers(int page, int limit) {
    List<UserDto> returnVal = new LinkedList<>();
    Pageable pageable = PageRequest.of(page, limit);
    Page<UserEntity> userEntities = userRepository.findAll(pageable);
    userEntities.getContent().forEach(userEntity ->
                                      {
                                        returnVal.add(new ModelMapper().map(userEntity, UserDto.class));
                                      });

    return returnVal;
  }

  @Override
  public boolean verifyEmailToken(String token) {
    UserEntity userEntity = userRepository.findUserByEmailVerificationToken(token);
    if (userEntity != null) {
      boolean hasTokenExpired = hasTokenExpired(token);
      if (!hasTokenExpired) {
        userEntity.setEmailVerificationStatus(Boolean.TRUE);
        userEntity.setEmailVerificationToken(null);
        userRepository.save(userEntity);
        return true;
      }
      return false;
    }
    return false;
  }

  @Override
  public boolean requestPasswordReset(String email) {
    boolean returnVal = false;

    UserEntity user = userRepository.findUserByEmail(email);
    if (user == null) {
      return returnVal;
    }
    String token = generatePasswordResetToken(user.getUserId());
    PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
    passwordResetTokenEntity.setToken(token);
    passwordResetTokenEntity.setUserDetail(user);
    passwordResetTokenRepository.save(passwordResetTokenEntity);
    returnVal = smsService.sendPasswordResetRequest(user.getFirstName(),
                                                    user.getEmail(),
                                                    token);
    return returnVal;
  }

  @Override
  public boolean resetPassword(String token, String password) {
    boolean returnVal = false;
    if (hasTokenExpired(token)) {
      return returnVal;
    }
    PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(
        token);
    if (passwordResetTokenEntity == null) {
      return returnVal;
    }
    String encodedPassword = bCryptPasswordEncoder.encode(password);
    UserEntity userEntity = passwordResetTokenEntity.getUserDetail();
    userEntity.setEncryptedPassword(encodedPassword);
    UserEntity savedUser = userRepository.save(userEntity);
    if (savedUser != null && savedUser.getEncryptedPassword().equals(encodedPassword)) {
      returnVal = true;
      passwordResetTokenRepository.delete(passwordResetTokenEntity);
    }

    return returnVal;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findUserByEmail(email);
    if (userEntity == null) {
      throw new UsernameNotFoundException(email);
    }
    return new User(userEntity.getEmail(),
                    userEntity.getEncryptedPassword(),
                    userEntity.getEmailVerificationStatus(),
                    true,
                    true,
                    true,
                    new ArrayList<>());

  }
}
