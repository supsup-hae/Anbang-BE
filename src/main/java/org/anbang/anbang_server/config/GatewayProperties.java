package org.anbang.anbang_server.config;

import java.net.URL;
import java.nio.file.Path;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@ConfigurationProperties(prefix = "gateway")
public class GatewayProperties {

    private String networkUrlCa1;
    private String networkUrlCa2;
    private URL walletUrl;
    private String walletName;
    private Path networkConfigOrg1;
    private Path networkConfigOrg2;
    private String channelName;
    private String chaincodeName;
    private boolean discovery;

}

