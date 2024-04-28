package org.example.anbang_server.service;

import org.example.anbang_server.dto.RealEstateDto;
import org.example.anbang_server.model.TransactionContext;
import org.springframework.http.ResponseEntity;

public interface RealEstateService {

  ResponseEntity<String> realEstateExists(TransactionContext ctx, String homeID);

  ResponseEntity<String> createRealEstate(TransactionContext ctx, RealEstateDto realEstateDto);

  ResponseEntity<String> searchRealEstate(TransactionContext ctx, String homeID);

  ResponseEntity<String> updateRealEstate(TransactionContext ctx, RealEstateDto realEstateDto);

  ResponseEntity<String> transferRealEstate(TransactionContext ctx, String homeID, String newOwner);

  ResponseEntity<String> deleteRealEstate(TransactionContext ctx, String homeID);

  ResponseEntity<String> queryAllRealEstate(TransactionContext ctx);

  void buildChannelCA();

  void deployChainCode();

  ResponseEntity<String> enrollClient(String userId);
}
