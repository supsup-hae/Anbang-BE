package org.anbang.anbang_server.service.impl;

import org.anbang.anbang_server.config.GatewayConfiguration;
import org.anbang.anbang_server.config.GatewayProperties;
import org.anbang.anbang_server.service.GateWayService;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GatewayServiceImpl implements GateWayService {

  private final GatewayConfiguration gatewayConfiguration;
  private final GatewayProperties gatewayProperties;

  @Autowired
  public GatewayServiceImpl(GatewayConfiguration gatewayConfiguration, GatewayProperties gatewayProperties) {
    this.gatewayConfiguration = gatewayConfiguration;
    this.gatewayProperties = gatewayProperties;
  }

  public Gateway connectToGateway(String userId) throws Exception {
    Gateway.Builder builder = Gateway.createBuilder();
    builder
        .identity(gatewayConfiguration.wallet(), userId)
        .networkConfig(gatewayProperties.getNetworkConfig())
        .discovery(gatewayProperties.isDiscovery());

    return builder.connect();
  }

  public Network network(String userId) throws Exception {
    return connectToGateway(userId).getNetwork(gatewayProperties.getChannelName());
  }

  public Contract contract(String userId) throws Exception {
    return network(userId).getContract(gatewayProperties.getChaincodeName());
  }

  public ResponseEntity<String> createAnbangRealEstate(String userId, String homeId, String owner, String address, String price) {

    try (Gateway gateway = connectToGateway(userId)) {
      Contract contract = contract(userId);
      byte[] response = contract.submitTransaction("createAnbangRealEstate", homeId, owner, address, price);
      return new ResponseEntity<>("매물 생성 완료", HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>("매물 생성 실패", HttpStatus.BAD_REQUEST);
  }
}
