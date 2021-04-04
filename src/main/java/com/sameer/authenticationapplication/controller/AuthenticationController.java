package com.sameer.authenticationapplication.controller;

import com.sameer.authenticationapplication.request.LoginRequestModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  @ApiResponses(value = {@ApiResponse(code = 200, message = "Response header", responseHeaders = {

      @ResponseHeader(name = "authorization",
                      description = "jwt token",
                      response = String.class),
      @ResponseHeader(name = "userId",
                      description = "public User Id",
                      response = String.class)
  })})

  @ApiOperation("User's login")
  @PostMapping("/users/login")
  public void fakeLogin(@RequestBody LoginRequestModel loginRequestModel) {
    throw new IllegalStateException(
        "This method should not be called Implemented by spring security");
  }

}
