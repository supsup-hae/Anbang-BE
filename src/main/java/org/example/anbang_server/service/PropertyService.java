package org.example.anbang_server.service;

import org.example.anbang_server.model.Property;
import org.springframework.http.ResponseEntity;

public interface PropertyService {
  ResponseEntity<String> propertyRegistration(Property property);

  void buildChannelCA();
}
