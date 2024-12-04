package dev.httpmarco.netline.client;

import dev.httpmarco.netline.common.AbstractNetConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(fluent = true)
public final class NetClientConfig extends AbstractNetConfig {

    public String id = UUID.randomUUID().toString();

}
