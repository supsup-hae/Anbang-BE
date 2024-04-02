package org.example.anbang_server.service;

import org.example.anbang_server.model.TransactionContext;
import org.example.anbang_server.model.RealEstate;
import org.springframework.http.ResponseEntity;

public interface RealEstateService {

  ResponseEntity<String> realEstateExists(TransactionContext ctx, String homeID);

  ResponseEntity<String> createRealEstate(TransactionContext ctx, RealEstate realEstate);

  ResponseEntity<String> searchRealEstate(TransactionContext ctx, String homeID);

  ResponseEntity<String> updateRealEstate(TransactionContext ctx, RealEstate realEstate);

  ResponseEntity<String> transferRealEstate(TransactionContext ctx, String homeID, String newOwner);

  ResponseEntity<String> deleteRealEstate(TransactionContext ctx, String homeID);

  ResponseEntity<String> queryAllRealEstate(TransactionContext ctx);


  void buildChannelCA();
}
