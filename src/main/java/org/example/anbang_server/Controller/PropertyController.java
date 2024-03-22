package org.example.anbang_server.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/anbang")
public class PropertyController {

  @GetMapping("/")
  public ResponseEntity<String> propertyRegistration() {
    return new ResponseEntity<>(HttpStatus.OK);
  }


}
