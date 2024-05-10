package org.anbang.anbang_server.Controller;

import lombok.extern.slf4j.Slf4j;
import org.anbang.anbang_server.dto.AdminDto;
import org.anbang.anbang_server.dto.RealEstateDto;
import org.anbang.anbang_server.dto.UserDto;
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
    return gateWayService.createAnbangRealEstate(realEstateDto.getUserId(),
        realEstateDto.getHomeId(),
        realEstateDto.getOwner(),
        realEstateDto.getAddress(),
        realEstateDto.getPrice());
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) throws Exception {
    return anbangCAUserService.registerUser(userDto.getUserId(), userDto.getAffiliation(), userDto.getAffiliation());
  }

  @PostMapping("/enroll")
  public ResponseEntity<String> enrollAdmin(@RequestBody AdminDto adminDto) throws Exception {
    return anbangCAUserService.enrollAdmin(adminDto.getAdminId(), adminDto.getAdminPw(), adminDto.getAffiliation());
  }

  @PostMapping("/script")
  public ResponseEntity<String> testScript(@RequestBody AdminDto adminDto) throws Exception {
    return propertyService.testScript(adminDto);
  }


  @GetMapping("/build")
  public ResponseEntity<String> buildServerCA() {
    propertyService.buildChannelCA();
    return new ResponseEntity<>("Fabric CA Channel Built", HttpStatus.CREATED);
  }

  @GetMapping("/deploy")
  public ResponseEntity<String> deployChainCode() {
    propertyService.deployChainCode();
    return new ResponseEntity<>("ChainCode Deploy Success", HttpStatus.OK);
  }

  @GetMapping("/connection")
  public ResponseEntity<String> createNetworkConnection() {
    propertyService.createNetworkConnection();
    return new ResponseEntity<>("Network Connection Created", HttpStatus.CREATED);
  }

  @GetMapping("/search")
  public ResponseEntity<String> searchRealEstate(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.searchRealEstate(realEstateDto.getHomeId());
  }

  @PostMapping("/update")
  public ResponseEntity<String> updateRealEstate(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.updateRealEstate(realEstateDto);
  }

  @PostMapping("/transfer")
  public ResponseEntity<String> transferRealEstate(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.transferRealEstate(realEstateDto.getHomeId(), realEstateDto.getOwner());
  }

  @GetMapping("/delete")
  public ResponseEntity<String> deleteRealEstate(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.deleteRealEstate(realEstateDto.getHomeId());
  }

  @GetMapping("/exists")
  public ResponseEntity<String> realEstateExists(@RequestBody RealEstateDto realEstateDto) {
    return propertyService.realEstateExists(realEstateDto.getHomeId());
  }

  @GetMapping("/query-all")
  public ResponseEntity<String> queryAllRealEstate() {
    return propertyService.queryAllRealEstate();
  }

}
