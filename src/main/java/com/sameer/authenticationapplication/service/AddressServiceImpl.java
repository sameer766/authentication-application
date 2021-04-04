package com.sameer.authenticationapplication.service;


import com.sameer.authenticationapplication.entity.AddressEntity;
import com.sameer.authenticationapplication.entity.UserEntity;
import com.sameer.authenticationapplication.repository.AddressRepository;
import com.sameer.authenticationapplication.repository.UserRepository;
import com.sameer.authenticationapplication.shared.AddressDto;
import java.util.LinkedList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

  @Autowired
  UserRepository userRepository;
  @Autowired
  AddressRepository addressRepository;

  @Override
  public List<AddressDto> getListOfAddress(String userId) {
    List<AddressDto> returnVal = new LinkedList<>();
    UserEntity userById = userRepository.findUserByUserId(userId);
    if (userById == null) {
      return returnVal;
    }
    Iterable<AddressEntity> addressEntities = addressRepository.findAllByUserDto(userById);
    addressEntities.forEach(addressEntity -> returnVal.add(new ModelMapper().map(addressEntity,
                                                                                 AddressDto.class)));
    return returnVal;
  }

  @Override
  public AddressDto getAddress(String addressId) {
    AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
    return  new ModelMapper().map(addressEntity, AddressDto.class);
  }
}
