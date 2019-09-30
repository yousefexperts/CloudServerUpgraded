package cn.insight.configuration.props.rpcs;

import lombok.Data;

import java.io.Serializable;

@Data
public class DecorationServerProperties implements Serializable {
    private DecorationServerFeignProperties feign = new DecorationServerFeignProperties();
}
