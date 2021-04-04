package com.sameer.authenticationapplication.response;

import com.sameer.authenticationapplication.request.AddressRequestModel;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetailsResponseModel {
  private String userId;
  private String firstName;
  private String lastName;
  private String email;
  private List<AddressResponse> addresses;
}
