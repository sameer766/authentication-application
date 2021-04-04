package com.sameer.authenticationapplication.response;

import com.sameer.authenticationapplication.shared.UserDto;
import java.io.Serializable;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse  extends RepresentationModel {
  private String addressId;
  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;
}

