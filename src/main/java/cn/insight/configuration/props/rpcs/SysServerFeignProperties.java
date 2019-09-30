package cn.insight.configuration.props.rpcs;

import lombok.Data;

@Data
public class SysServerFeignProperties {
    private String protocol = "http";
    private String name = "jbone-sys-server";
}
