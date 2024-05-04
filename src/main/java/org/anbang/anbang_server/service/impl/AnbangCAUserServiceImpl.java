package org.anbang.anbang_server.service.impl;

import org.anbang.anbang_server.config.CAClientConfiguration;
import org.anbang.anbang_server.config.CAUserConfiguration;
import org.anbang.anbang_server.config.GatewayConfiguration;
import org.anbang.anbang_server.config.GatewayProperties;
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

import java.io.IOException;

@Service
public class AnbangCAUserServiceImpl implements AnbangCAUserService {

  private final CAClientConfiguration caClient;
  private final GatewayConfiguration gatewayConfiguration;
  private final GatewayProperties gatewayProperties;

  private static final String BUYER = "buyer";
  private static final String SELLER = "seller";

  @Autowired
  public AnbangCAUserServiceImpl(CAClientConfiguration caClient, GatewayConfiguration gatewayConfiguration, GatewayProperties gatewayProperties){
    this.caClient = caClient;
    this.gatewayConfiguration = gatewayConfiguration;
    this.gatewayProperties = gatewayProperties;
  }

  @Override
  public ResponseEntity<String> enrollAdmin(String adminId, String adminPw, String affiliation) throws Exception {

    if (userExists(adminId)) {
      return new ResponseEntity<>("이미 등록된 관리자입니다.", HttpStatus.OK);
    }

    final EnrollmentRequest enrollmentRequest = new EnrollmentRequest();
    enrollmentRequest.setProfile("tls");

    String orgMSPId = getOrgMspId(affiliation);
    if (orgMSPId == null) {
      return new ResponseEntity<>("OrgMSPId를 찾을 수 없습니다.", HttpStatus.OK);
    }
    switch (affiliation) {
      case BUYER:
        enrollmentRequest.addHost(gatewayProperties.getNetworkUrlCa1());
        Enrollment adminEnrollment = caClient.caClient1().enroll(adminId, adminPw, enrollmentRequest);
        Identity identity = Identities.newX509Identity(orgMSPId, adminEnrollment);
        gatewayConfiguration.wallet().put(adminId, identity);

        return new ResponseEntity<>("org1 관리자 등록 완료", HttpStatus.OK);

      case SELLER:
        enrollmentRequest.addHost(gatewayProperties.getNetworkUrlCa2());
        Enrollment adminEnrollment2 = caClient.caClient2().enroll(adminId, adminPw, enrollmentRequest);
        Identity identity2 = Identities.newX509Identity(orgMSPId, adminEnrollment2);
        gatewayConfiguration.wallet().put(adminId, identity2);

        return new ResponseEntity<>("org2 관리자 등록 완료", HttpStatus.OK);

      default:
        return new ResponseEntity<>("관리자 등록 실패", HttpStatus.OK);
    }
  }

  @Override
  public ResponseEntity<String> registerUser(String userId, String affiliation,String adminId) throws Exception {

    if (userExists(userId)) {
      return new ResponseEntity<>("이미 등록된 유저입니다.", HttpStatus.OK);
    }

    X509Identity adminIdentity = (X509Identity) gatewayConfiguration.wallet().get(adminId);
    if (adminIdentity == null) {
      return new ResponseEntity<>("관리자가 등록되지 않았습니다.", HttpStatus.OK);
    }

    String orgMSPId = getOrgMspId(affiliation);
    if (orgMSPId == null) {
      return new ResponseEntity<>("OrgMSPId를 찾을 수 없습니다.", HttpStatus.OK);
    }

    CAUserConfiguration admin = CAUserConfiguration.builder()
        .userId(adminId)
        .orgMSP(orgMSPId)
        .affiliation(affiliation)
        .identity(adminIdentity)
        .roles(null)
        .account(null)
        .build();

    RegistrationRequest registrationRequest = new RegistrationRequest(userId);
    switch (affiliation) {
      case BUYER:
        registrationRequest.setAffiliation(affiliation);
        registrationRequest.setEnrollmentID(userId);
        String enrollmentSecret = caClient.caClient1().register(registrationRequest, admin);
        Enrollment enrollment = caClient.caClient1().enroll(userId, enrollmentSecret);
        Identity identity = Identities.newX509Identity(orgMSPId, enrollment);
        gatewayConfiguration.wallet().put(userId, identity);

        return new ResponseEntity<>("org1 유저 등록 완료", HttpStatus.OK);
      case SELLER:
        registrationRequest.setAffiliation(affiliation);
        registrationRequest.setEnrollmentID(userId);
        String enrollmentSecret2 = caClient.caClient2().register(registrationRequest, admin);
        Enrollment enrollment2 = caClient.caClient2().enroll(userId, enrollmentSecret2);
        Identity identity2 = Identities.newX509Identity(orgMSPId, enrollment2);
        gatewayConfiguration.wallet().put(userId, identity2);

        return new ResponseEntity<>("org2 유저 등록 완료", HttpStatus.OK);
      default:
        return new ResponseEntity<>("유저 등록 실패", HttpStatus.NOT_FOUND);
    }
  }
  public Boolean userExists(String userId) throws IOException {
    return gatewayConfiguration.wallet().get(userId) != null;
  }

  private String getOrgMspId(String affiliation) {
    switch (affiliation) {
      case BUYER:
        return "OrgMSP1";
      case SELLER:
        return "OrgMSP2";
      default:
        return null;
    }
  }
}
