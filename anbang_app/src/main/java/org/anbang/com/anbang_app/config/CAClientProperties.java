package org.anbang.com.anbang_app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

@Getter
@Setter
@ConfigurationProperties(prefix = "ca")
public class CAClientProperties {
        private String caName1;
        private String caName2;
        private String caUrl1;
        private String caUrl2;
        private String pemFile1;
        private String pemFile2;
        private boolean allowAllHostNames;
}
