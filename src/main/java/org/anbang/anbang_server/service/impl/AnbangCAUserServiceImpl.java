package org.anbang.anbang_server.service.impl;

import org.anbang.anbang_server.config.CAClientConfiguration;
import org.anbang.anbang_server.config.CAUserConfiguration;
import org.anbang.anbang_server.config.GatewayConfiguration;
import org.anbang.anbang_server.service.AnbangCAUserService;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.X509Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AnbangCAUserServiceImpl implements AnbangCAUserService {

  private final CAClientConfiguration caClient;
  private final GatewayConfiguration gatewayConfiguration;

  @Autowired
  public AnbangCAUserServiceImpl(CAClientConfiguration caClient, GatewayConfiguration gatewayConfiguration) {
    this.caClient = caClient;
    this.gatewayConfiguration = gatewayConfiguration;
  }

  @Override
  public ResponseEntity<String> enrollAdmin(String adminId, String adminPw, String orgMspId) throws Exception {

    final EnrollmentRequest enrollmentRequest = new EnrollmentRequest();
    enrollmentRequest.setProfile("tls");

    switch (orgMspId) {
      case "org1":
        enrollmentRequest.addHost("org1.example.com");
        Enrollment adminEnrollment = caClient.caClient1().enroll(adminId, adminPw, enrollmentRequest);
        Identity user = Identities.newX509Identity(orgMspId, adminEnrollment);
        gatewayConfiguration.wallet().put(adminId, user);
        return new ResponseEntity<>("org1 관리자 등록 완료", HttpStatus.OK);
      case "org2":
        enrollmentRequest.addHost("org2.example.com");
        Enrollment adminEnrollment2 = caClient.caClient2().enroll(adminId, adminPw, enrollmentRequest);
        Identity user2 = Identities.newX509Identity(orgMspId, adminEnrollment2);
        gatewayConfiguration.wallet().put(adminId, user2);
        return new ResponseEntity<>("org2 관리자 등록 완료", HttpStatus.OK);
      default:
        return new ResponseEntity<>("관리자 등록 실패", HttpStatus.OK);
    }
  }

  @Override
  public ResponseEntity<String> registerUser(String userId, String affiliation, String orgMspId, String adminId) throws Exception {

    X509Identity adminIdentity = (X509Identity) gatewayConfiguration.wallet().get(adminId);
    CAUserConfiguration admin = CAUserConfiguration.builder()
        .userId(adminId)
        .orgMSP(orgMspId)
        .affiliation(affiliation)
        .identity(adminIdentity)
        .roles(null)
        .account(null)
        .build();

    RegistrationRequest registrationRequest = new RegistrationRequest(userId);
    switch (orgMspId) {
      case "org1":
        registrationRequest.setAffiliation(affiliation);
        registrationRequest.setEnrollmentID(userId);
        String enrollmentSecret = caClient.caClient1().register(registrationRequest, admin);
        Enrollment enrollment = caClient.caClient1().enroll(userId, enrollmentSecret);
        Identity user = Identities.newX509Identity(orgMspId, enrollment);
        gatewayConfiguration.wallet().put(userId, user);
        return new ResponseEntity<>("org1 유저 등록 완료", HttpStatus.OK);
      case "org2":
        registrationRequest.setAffiliation(affiliation);
        registrationRequest.setEnrollmentID(userId);
        String enrollmentSecret2 = caClient.caClient2().register(registrationRequest, admin);
        Enrollment enrollment2 = caClient.caClient2().enroll(userId, enrollmentSecret2);
        Identity user2 = Identities.newX509Identity(orgMspId, enrollment2);
        gatewayConfiguration.wallet().put(userId, user2);
        return new ResponseEntity<>("org2 유저 등록 완료", HttpStatus.OK);
      default:
        return new ResponseEntity<>("유저 등록 실패", HttpStatus.NOT_FOUND);
    }
  }
}
