package org.anbang.anbang_server.Controller;

import lombok.extern.slf4j.Slf4j;
import org.anbang.anbang_server.dto.RealEstateDto;
import org.anbang.anbang_server.service.AnbangCAUserService;
import org.anbang.anbang_server.service.GateWayService;
import org.anbang.anbang_server.service.RealEstateService;
import org.anbang.anbang_server.service.impl.AnbangCAUserServiceImpl;
import org.anbang.anbang_server.service.impl.GatewayServiceImpl;
import org.anbang.anbang_server.service.impl.RealEstateServiceImpl;
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

  private final RealEstateService propertyService;
  private final GateWayService gateWayService;
  private final AnbangCAUserService anbangCAUserService;

  @Autowired
  public PropertyController(RealEstateServiceImpl propertyService, GatewayServiceImpl gateWayService, AnbangCAUserServiceImpl anbangCAUserService) {
    this.propertyService = propertyService;
    this.gateWayService = gateWayService;
    this.anbangCAUserService = anbangCAUserService;
  }

  @PostMapping("/create")
  public ResponseEntity<String> createRealEstate(@RequestBody RealEstateDto realEstateDto) {
    return gateWayService.createAnbangRealEstate(realEstateDto.getUserID(),
        realEstateDto.getHomeID(),
        realEstateDto.getOwner(),
        realEstateDto.getAddress(),
        realEstateDto.getPrice());
  }

//  @PostMapping("/register")
//  public ResponseEntity<String> enrollClient(@RequestBody UserDto userDto) {
//    return anbangCAUserService.registerUser(userDto.getUuid());
//  }

//  @PostMapping("/enroll")
//  public ResponseEntity<String> enrollClient(@RequestBody UserDto userDto) {
//    return anbangCAUserService.enrollAdmin(userDto.getUuid());
//  }

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


  @GetMapping("/search")
  public ResponseEntity<String> searchRealEstate(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.searchRealEstate(realEstateDto.getHomeID());
  }

  @PostMapping("/update")
  public ResponseEntity<String> updateRealEstate(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.updateRealEstate(realEstateDto);
  }

  @PostMapping("/transfer")
  public ResponseEntity<String> transferRealEstate(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.transferRealEstate(realEstateDto.getHomeID(), realEstateDto.getOwner());
  }

  @GetMapping("/delete")
  public ResponseEntity<String> deleteRealEstate(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.deleteRealEstate(realEstateDto.getHomeID());
  }

  @GetMapping("/exists")
  public ResponseEntity<String> realEstateExists(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.realEstateExists(realEstateDto.getHomeID());
  }

  @GetMapping("/query-all")
  public ResponseEntity<String> queryAllRealEstate() {
    return propertyService.queryAllRealEstate();
  }

}
