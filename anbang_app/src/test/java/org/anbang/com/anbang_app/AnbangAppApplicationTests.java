package org.anbang.com.anbang_app;

import org.anbang.com.anbang_app.config.CAClientConfiguration;
import org.anbang.com.anbang_app.config.CAClientProperties;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AnbangAppApplicationTests {
   @Autowired
    CAClientProperties caClientProperties;
   @Test
    void contextLoads() {
        System.out.println(caClientProperties.getCaName1());
        System.out.println(caClientProperties.getCaName2());
        System.out.println(caClientProperties.getCaUrl1());
        System.out.println(caClientProperties.getCaUrl2());
        System.out.println(caClientProperties.getPemFile1());
        System.out.println(caClientProperties.getPemFile2());
        System.out.println(caClientProperties.isAllowAllHostNames());

   }

}
