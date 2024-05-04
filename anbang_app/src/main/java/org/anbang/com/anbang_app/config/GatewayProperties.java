package org.anbang.com.anbang_app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;
import java.nio.file.Path;


@Getter
@Setter
@ConfigurationProperties(prefix = "gateway")
public class GatewayProperties {

    private URL walletUrl;
    private String walletName;
    private Path networkConfig;
    private String channelName;
    private String chaincodeName;
    private boolean discovery;

}

