package com.sameer.authenticationapplication.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetailsRequestModel {

  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private List<AddressRequestModel> addresses;
}
