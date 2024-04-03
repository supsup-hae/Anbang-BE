package org.example.anbang_server.Controller;

import lombok.extern.slf4j.Slf4j;
import org.example.anbang_server.model.RealEstate;
import org.example.anbang_server.model.TransactionContext;
import org.example.anbang_server.service.RealEstateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/anbang")
public class PropertyController {

  private final RealEstateServiceImpl propertyService;
  private TransactionContext context;

  @Autowired
  public PropertyController(RealEstateServiceImpl propertyService) {
    this.propertyService = propertyService;
  }

  @PostMapping("/create")
  public ResponseEntity<String> createRealEstate(RealEstate realEstate) {
    return propertyService.createRealEstate(context, realEstate);
  }

  @GetMapping("/search")
  public ResponseEntity<String> searchRealEstate(String homeID) {
    return propertyService.searchRealEstate(context, homeID);
  }

  @PostMapping("/update")
  public ResponseEntity<String> updateRealEstate(RealEstate realEstate) {
    return propertyService.updateRealEstate(context, realEstate);
  }

  @PostMapping("/transfer")
  public ResponseEntity<String> transferRealEstate(String homeID,
      String newOwner) {
    return propertyService.transferRealEstate(context, homeID, newOwner);
  }

  @GetMapping("/delete")
  public ResponseEntity<String> deleteRealEstate(String homeID) {
    return propertyService.deleteRealEstate(context, homeID);
  }

  @GetMapping("/exists")
  public ResponseEntity<String> realEstateExists(String homeID) {
    return propertyService.realEstateExists(context, homeID);
  }

  @GetMapping("/query-all")
  public ResponseEntity<String> queryAllRealEstate(TransactionContext ctx) {
    return propertyService.queryAllRealEstate(ctx);
  }

  @GetMapping("/test")
  public ResponseEntity<String> buildServerCA() {
    propertyService.buildChannelCA();
    return new ResponseEntity<>("Fabric CA Channel Built",HttpStatus.OK);
  }
}
