package com.sameer.authenticationapplication.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "users")
public class UserEntity implements Serializable {

  @Setter(AccessLevel.NONE)
  @Getter(AccessLevel.NONE)
  private static final long serialVersionUID = 45554938948938498L;
  @Id
  @GeneratedValue
  private long id;
  @Column(nullable = false)
  private String userId;
  @Column(nullable = false, length = 50)
  private String firstName;
  @Column(nullable = false, length = 50)
  private String lastName;
  @Column(nullable = false, length = 120, unique = true)
  private String email;
  @Column(nullable = false)
  private String encryptedPassword;
  private String emailVerificationToken;
  @OneToMany(mappedBy = "userDto", cascade = CascadeType.ALL)
  private List<AddressEntity> addresses;
  @Column(nullable = false, columnDefinition = "BOOLEAN")
  private Boolean emailVerificationStatus;
}
