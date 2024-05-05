package org.anbang.anbang_server.config;

import java.net.URL;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;


@Getter
@Setter
@ConfigurationProperties(prefix = "gateway")
public class GatewayProperties {

    private String networkUrlCa1;
    private String networkUrlCa2;
    private URL walletUrl;
    private String walletName;
    private Resource networkConfigOrg1;
    private Resource networkConfigOrg2;
    private String channelName;
    private String chaincodeName;
    private boolean discovery;

}

