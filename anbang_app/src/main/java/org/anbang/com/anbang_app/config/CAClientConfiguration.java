package org.anbang.com.anbang_app.config;

import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@EnableConfigurationProperties({CAClientProperties.class})
@Configuration
public class CAClientConfiguration {

    private final CAClientProperties caClientProperties;

    public CAClientConfiguration(CAClientProperties caClientProperties) {
        this.caClientProperties = caClientProperties;
    }

    @Bean
    public HFCAClient caClient1() throws Exception {
        return createHFCAClient(caClientProperties.getCaName1(), caClientProperties.getCaUrl1(), caClientProperties.getPemFile1(), caClientProperties.isAllowAllHostNames());
    }

    @Bean
    public HFCAClient caClient2() throws Exception {
        return createHFCAClient(caClientProperties.getCaName2(), caClientProperties.getCaUrl2(), caClientProperties.getPemFile2(), caClientProperties.isAllowAllHostNames());
    }

    private HFCAClient createHFCAClient(String caName, String caUrl, String pemFile, boolean allowAllHostNames) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("pemFile", pemFile);
        properties.put("allowAllHostNames", allowAllHostNames);
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        HFCAClient caClient = HFCAClient.createNewInstance(
                caName,
                caUrl,
                properties);
        caClient.setCryptoSuite(cryptoSuite);
        return caClient;
    }
}
