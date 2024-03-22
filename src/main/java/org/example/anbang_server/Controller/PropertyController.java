package org.example.anbang_server.Controller;

import lombok.extern.slf4j.Slf4j;
import org.example.anbang_server.model.Property;
import org.example.anbang_server.service.PropertyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/anbang")
public class PropertyController {

  private final PropertyServiceImpl propertyService;

  @Autowired
  public PropertyController(PropertyServiceImpl propertyService) {
    this.propertyService = propertyService;
  }

  @GetMapping("/")
  public ResponseEntity<String> propertyRegistration(Property property) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/test")
  public ResponseEntity<String> buildServerCA() {
    propertyService.buildChannelCA();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
