package org.anbang.com.anbang_app.service.impl;

import org.anbang.com.anbang_app.config.CAClientConfiguration;
import org.anbang.com.anbang_app.config.CAUserConfiguration;
import org.anbang.com.anbang_app.config.GatewayConfiguration;
import org.anbang.com.anbang_app.service.AnbangCAUserService;

import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.X509Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import org.springframework.stereotype.Service;

@Service
public class AnbangCAUserServiceImpl implements AnbangCAUserService {

    private final CAClientConfiguration caClient;
    private final GatewayConfiguration gatewayConfiguration;
    public AnbangCAUserServiceImpl(CAClientConfiguration caClient, GatewayConfiguration gatewayConfiguration) {
        this.caClient = caClient;
        this.gatewayConfiguration = gatewayConfiguration;
    }

    @Override
    public void enrollAdmin(String adminId, String adminPw, String orgMspId) throws Exception{

        final EnrollmentRequest enrollmentRequest = new EnrollmentRequest();
        enrollmentRequest.setProfile("tls");

        switch (orgMspId) {
            case "org1":
                enrollmentRequest.addHost("org1.example.com");
                Enrollment adminEnrollment = caClient.caClient1().enroll(adminId, adminPw, enrollmentRequest);
                Identity user = Identities.newX509Identity(orgMspId, adminEnrollment);
                gatewayConfiguration.wallet().put(adminId, user);
                break;
            case "org2":
                enrollmentRequest.addHost("org2.example.com");
                Enrollment adminEnrollment2 = caClient.caClient2().enroll(adminId, adminPw, enrollmentRequest);
                Identity user2 = Identities.newX509Identity(orgMspId, adminEnrollment2);
                gatewayConfiguration.wallet().put(adminId, user2);
                break;
            default:
                break;
        }
    }

    @Override
    public void registerUser(String userId, String affiliation, String orgMspId, String adminId) throws Exception {

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
                String enrollmentSecret = caClient.caClient1().register(registrationRequest,admin);
                Enrollment enrollment = caClient.caClient1().enroll(userId, enrollmentSecret);
                Identity user = Identities.newX509Identity(orgMspId, enrollment);
                gatewayConfiguration.wallet().put(userId, user);
                break;
            case "org2":
                registrationRequest.setAffiliation(affiliation);
                registrationRequest.setEnrollmentID(userId);
                String enrollmentSecret2 = caClient.caClient2().register(registrationRequest,admin);
                Enrollment enrollment2 = caClient.caClient2().enroll(userId, enrollmentSecret2);
                Identity user2 = Identities.newX509Identity(orgMspId, enrollment2);
                gatewayConfiguration.wallet().put(userId, user2);
                break;
            default:
                break;
        }
    }
}
