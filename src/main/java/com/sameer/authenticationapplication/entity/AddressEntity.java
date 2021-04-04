package com.sameer.authenticationapplication.entity;

import com.sameer.authenticationapplication.shared.UserDto;
import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "addresses")
public class AddressEntity implements Serializable {

  @Setter(AccessLevel.NONE)
  @Getter(AccessLevel.NONE)
  private static final long serialVersionUID = 45554938948638498L;
  @Id
  @GeneratedValue
  private long id;
  @Column(length = 30, nullable = false)
  private String addressId;
  @Column(length = 15, nullable = false)
  private String city;
  @Column(length = 15, nullable = false)
  private String country;
  @Column(nullable = false, length = 100)
  private String streetName;
  @Column(nullable = false, length = 7)
  private String postalCode;
  @Column(nullable = false, length = 10)
  private String type;
  @ManyToOne
  @JoinColumn(name = "users_id")
  private UserEntity userDto;
}
