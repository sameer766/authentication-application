package com.sameer.authenticationapplication.repository;

import com.sameer.authenticationapplication.entity.AddressEntity;
import com.sameer.authenticationapplication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository  extends JpaRepository<AddressEntity,Long> {
  Iterable<AddressEntity> findAllByUserDto(UserEntity userById);
  AddressEntity findByAddressId(String addressId);
}
