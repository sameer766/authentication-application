package com.sameer.authenticationapplication.service;

import com.sameer.authenticationapplication.shared.AddressDto;
import java.util.List;

public interface AddressService {
   List<AddressDto> getListOfAddress(String userId);
   AddressDto getAddress(String addressId);
}
