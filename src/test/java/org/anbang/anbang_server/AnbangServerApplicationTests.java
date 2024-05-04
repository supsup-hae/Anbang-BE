package org.anbang.anbang_server;

import org.anbang.anbang_server.config.CAClientProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnbangServerApplicationTests {

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
