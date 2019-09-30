package cn.insight.configuration.props.rpcs;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysServerProperties implements Serializable {
    private SysServerFeignProperties feign = new SysServerFeignProperties();
}
