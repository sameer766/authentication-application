package com.sameer.authenticationapplication.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity(name = "password_reset_token")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetTokenEntity implements Serializable {

  @GeneratedValue
  @Id
  private long id;
  private String token;
  @JoinColumn(name = "users_id")
  @OneToOne
  private UserEntity userDetail;

}
