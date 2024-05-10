package org.anbang.anbang_server.service;

import org.anbang.anbang_server.dto.AdminDto;
import org.anbang.anbang_server.dto.RealEstateDto;
import org.springframework.http.ResponseEntity;

public interface RealEstateService {

  ResponseEntity<String> realEstateExists(String homeID);

  ResponseEntity<String> searchRealEstate(String homeID);

  ResponseEntity<String> updateRealEstate(RealEstateDto realEstateDto);

  ResponseEntity<String> transferRealEstate(String homeID, String newOwner);

  ResponseEntity<String> deleteRealEstate(String homeID);

  ResponseEntity<String> queryAllRealEstate();

  void buildChannelCA();

  void deployChainCode();

  void createNetworkConnection();

  void testScript(AdminDto adminDto);

}
