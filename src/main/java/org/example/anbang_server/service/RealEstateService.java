package org.example.anbang_server.service;

import org.example.anbang_server.dto.RealEstateDto;
import org.springframework.http.ResponseEntity;

public interface RealEstateService {

  ResponseEntity<String> realEstateExists(String homeID);

  ResponseEntity<String> createRealEstate(RealEstateDto realEstateDto);

  ResponseEntity<String> searchRealEstate(String homeID);

  ResponseEntity<String> updateRealEstate(RealEstateDto realEstateDto);

  ResponseEntity<String> transferRealEstate(String homeID, String newOwner);

  ResponseEntity<String> deleteRealEstate(String homeID);

  ResponseEntity<String> queryAllRealEstate();

  void buildChannelCA();

  void deployChainCode();

  ResponseEntity<String> enrollClient(String userId);
}
