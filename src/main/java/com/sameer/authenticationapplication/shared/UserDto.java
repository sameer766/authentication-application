package com.sameer.authenticationapplication.shared;

import com.sameer.authenticationapplication.request.AddressRequestModel;
import java.io.Serializable;
import java.util.List;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
  @Setter(AccessLevel.NONE)
  @Getter(AccessLevel.NONE)
  private static final long serialVersionUID=4384938948938498L;
  private long id;
  private String userId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String encryptedPassword;
  private String emailVerificationToken;
  private Boolean emailVerificationStatus=false;
  private List<AddressDto> addresses;

}
