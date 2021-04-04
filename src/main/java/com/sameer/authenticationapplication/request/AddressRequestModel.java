package com.sameer.authenticationapplication.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressRequestModel {
  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;
}
