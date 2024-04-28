package org.example.anbang_server.Controller;

import lombok.extern.slf4j.Slf4j;
import org.example.anbang_server.dto.RealEstateDto;
import org.example.anbang_server.dto.UserDto;
import org.example.anbang_server.model.TransactionContext;
import org.example.anbang_server.service.RealEstateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public ResponseEntity<String> createRealEstate(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.createRealEstate(context, realEstateDto);
  }

  @GetMapping("/search")
  public ResponseEntity<String> searchRealEstate(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.searchRealEstate(context, realEstateDto.getHomeID());
  }

  @PostMapping("/update")
  public ResponseEntity<String> updateRealEstate(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.updateRealEstate(context, realEstateDto);
  }

  @PostMapping("/transfer")
  public ResponseEntity<String> transferRealEstate(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.transferRealEstate(context, realEstateDto.getHomeID(), realEstateDto.getOwner());
  }

  @GetMapping("/delete")
  public ResponseEntity<String> deleteRealEstate(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.deleteRealEstate(context, realEstateDto.getHomeID());
  }

  @GetMapping("/exists")
  public ResponseEntity<String> realEstateExists(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.realEstateExists(context, realEstateDto.getHomeID());
  }

  @GetMapping("/query-all")
  public ResponseEntity<String> queryAllRealEstate(TransactionContext ctx) {
    return propertyService.queryAllRealEstate(ctx);
  }

  @GetMapping("/build")
  public ResponseEntity<String> buildServerCA() {
    propertyService.buildChannelCA();
    return new ResponseEntity<>("Fabric CA Channel Built", HttpStatus.OK);
  }

  @GetMapping("/deploy")
  public ResponseEntity<String> deployChainCode() {
    propertyService.deployChainCode();
    return new ResponseEntity<>("ChainCode Deploy Success", HttpStatus.OK);
  }

  @PostMapping("/enroll")
  public ResponseEntity<String> enrollClient(@RequestBody UserDto userDto) {
    return propertyService.enrollClient(userDto.getId());
  }

}
