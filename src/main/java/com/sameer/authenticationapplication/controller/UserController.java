package com.sameer.authenticationapplication.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.sameer.authenticationapplication.exceptions.UserServiceException;
import com.sameer.authenticationapplication.request.PasswordResetModel;
import com.sameer.authenticationapplication.request.PasswordResetRequestModel;
import com.sameer.authenticationapplication.request.UserDetailsRequestModel;
import com.sameer.authenticationapplication.response.*;
import com.sameer.authenticationapplication.service.AddressService;
import com.sameer.authenticationapplication.service.UserService;
import com.sameer.authenticationapplication.shared.AddressDto;
import com.sameer.authenticationapplication.shared.UserDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  AddressService addressService;

  @GetMapping(path = "/{id}",
              produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  @ApiImplicitParams( {
                          @ApiImplicitParam(name = "authorization",
                                            value = "jwt token",
                                            paramType = "header")
                      }

  )
  public UserDetailsResponseModel getUser(@PathVariable String id) {
    UserDto userById = userService.getUserById(id);
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(userById, UserDetailsResponseModel.class);

  }

  @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
               consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public UserDetailsResponseModel createUser(
      @RequestBody UserDetailsRequestModel userDetailsRequestModel) throws Exception {

    if (userDetailsRequestModel.getFirstName().isEmpty()) {
      throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
    }
    ModelMapper modelMapper = new ModelMapper();
    UserDto userDto = modelMapper.map(userDetailsRequestModel, UserDto.class);
    UserDto createdUser = userService.createUser(userDto);
    UserDetailsResponseModel userDetailsResponseModel = modelMapper.map(createdUser,
                                                                        UserDetailsResponseModel.class);

    return userDetailsResponseModel;
  }

  @ApiImplicitParams( {
                          @ApiImplicitParam(name = "authorization",
                                            value = "jwt token",
                                            paramType = "header")
                      }

  )
  @PutMapping(value = "/{userId}",
              produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
              consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public UserDetailsResponseModel updateUser(
      @RequestBody UserDetailsRequestModel userDetailsRequestModel,
      @PathVariable String userId) {
    if (userId == null || userId.isEmpty()) {
      throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
    }
    ModelMapper modelMapper = new ModelMapper();
    UserDto userDto = modelMapper.map(userDetailsRequestModel, UserDto.class);
    UserDto updateUser = userService.updateUser(userId, userDto);


    return modelMapper.map(updateUser, UserDetailsResponseModel.class);
  }

  @ApiImplicitParams( {
                          @ApiImplicitParam(name = "authorization",
                                            value = "jwt token",
                                            paramType = "header")
                      }

  )
  @DeleteMapping(value = "{userId}",
                 produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public Response deleteUser(@PathVariable String userId) {
    if (userId == null || userId.isEmpty()) {
      throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
    }
    userService.deleteUser(userId);
    return new Response(OperationName.DELETE.name(), OperationStatus.SUCCESS.name());

  }

  @ApiImplicitParams( {
                          @ApiImplicitParam(name = "authorization",
                                            value = "jwt token",
                                            paramType = "header")
                      }

  )
  @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public List<UserDetailsResponseModel> getUsers(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int limit) {
    List<UserDto> userDtos = userService.getAllUsers(page, limit);
    List<UserDetailsResponseModel> userDetailsResponseModelList = new LinkedList<>();
    userDtos.forEach(userDto -> {
      UserDetailsResponseModel userDetailsResponseModel = new ModelMapper().map(userDto,
                                                                                UserDetailsResponseModel.class);
      userDetailsResponseModelList.add(userDetailsResponseModel);
    });
    return userDetailsResponseModelList;
  }


  @ApiImplicitParams( {
                          @ApiImplicitParam(name = "authorization",
                                            value = "jwt token",
                                            paramType = "header")
                      }

  )
  @GetMapping(value = "{userId}/addresses",
              produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public List<AddressResponse> getAddressForUser(@PathVariable String userId) {
    List<AddressResponse> addressResponseList = new LinkedList<>();
    List<AddressDto> addressDtoList = addressService.getListOfAddress(userId);
    if (addressDtoList != null && !addressDtoList.isEmpty()) {
      Type listType = new TypeToken<List<AddressResponse>>() {
      }.getType();
      addressResponseList = new ModelMapper().map(addressDtoList, listType);
    }


    for (AddressResponse addressResponse : addressResponseList) {
      Link user = linkTo(methodOn(UserController.class).getUser(userId)).withRel("user");
      addressResponse.add(user);
      Link addressesLink = linkTo(methodOn(UserController.class).getAddressForUser(userId)).withSelfRel();
      addressResponse.add(addressesLink);
    }
    return addressResponseList;

  }

  @ApiImplicitParams( {
                          @ApiImplicitParam(name = "authorization",
                                            value = "jwt token",
                                            paramType = "header")
                      }

  )
  @GetMapping(value = "{userId}/addresses/{addressId}",
              produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public AddressResponse getAddressForAddressId(@PathVariable String addressId,
                                                @PathVariable String userId) {
    AddressDto address = addressService.getAddress(addressId);
    Link addresses = linkTo(methodOn(UserController.class).getAddressForAddressId(userId,
                                                                                  addressId)).withSelfRel();
    Link user = linkTo(UserController.class).slash(userId).withRel("user");
    Link addressesLink = linkTo(methodOn(UserController.class).getAddressForUser(userId)).withRel(
        "addresses");
    AddressResponse addressResponse = new ModelMapper().map(address, AddressResponse.class);
    addressResponse.add(addresses);
    addressResponse.add(user);
    addressResponse.add(addressesLink);
    return addressResponse;
  }


  //  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping(value = "/email-verification",
              produces = {MediaType.APPLICATION_JSON_VALUE})
  public Response getAddressForAddressId(@RequestParam String token) {
    Response response = new Response();
    response.setName(OperationName.VERIFY_EMAIL.name());
    boolean isVerified = userService.verifyEmailToken(token);
    if (isVerified) {
      response.setResult(OperationStatus.SUCCESS.name());
    } else {
      response.setResult(OperationStatus.ERROR.name());
    }
    return response;
  }


  @PostMapping(value = "/password-reset",
               produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
               consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public Response requestPasswordReset(
      @RequestBody PasswordResetRequestModel passwordResetRequestModel) throws Exception {
    Response response = new Response();
    boolean result = userService.requestPasswordReset(passwordResetRequestModel.getEmail());
    response.setName(OperationName.REQUEST_PASSWORD_RESET.name());
    response.setResult(OperationStatus.ERROR.name());
    if (result) {
      response.setResult(OperationStatus.SUCCESS.name());
    }
    return response;
  }

  @PostMapping(value = "/reset-password",
               produces = MediaType.APPLICATION_JSON_VALUE,
               consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public Response passwordReset(
      @RequestBody PasswordResetModel passwordResetModel) throws Exception {
    Response response = new Response();
    boolean result = userService.resetPassword(passwordResetModel.getToken(),
                                               passwordResetModel.getPassword());
    response.setName(OperationName.PASSWORD_RESET.name());
    response.setResult(OperationStatus.ERROR.name());
    if (result) {
      response.setResult(OperationStatus.SUCCESS.name());
    }
    return response;
  }

}
