/*
package org.anbang.anbang_server.config;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({GatewayProperties.class})
@Configuration
public class GatewayConfiguration {
    private final GatewayProperties gatewayProperties;

    public GatewayConfiguration(GatewayProperties gatewayProperties) {
        this.gatewayProperties = gatewayProperties;
    }

@Bean
    public Wallet wallet() {
        return Wallets.newCouchDBWallet(gatewayProperties.getWalletUrl(), gatewayProperties.getWalletName());
    }

}
*/
