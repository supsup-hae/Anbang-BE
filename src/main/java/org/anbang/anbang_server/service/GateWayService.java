package org.anbang.anbang_server.service;

import org.springframework.http.ResponseEntity;

public interface GateWayService {
    ResponseEntity<String> createAnbangRealEstate(String userId, String homeId, String owner, String address, String price);
}
