package org.anbang.anbang_server.service;

import org.springframework.http.ResponseEntity;

public interface AnbangCAUserService {

  ResponseEntity<String> enrollAdmin(String adminId, String adminPw, String orgMspId) throws Exception;

  ResponseEntity<String> registerUser(String userId, String affiliation, String adminId) throws Exception;
}