package com.sameer.authenticationapplication.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordResetModel {
  private String password;
  private String token;
}
