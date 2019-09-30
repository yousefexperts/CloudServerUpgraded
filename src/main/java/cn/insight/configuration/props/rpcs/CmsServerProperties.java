package cn.insight.configuration.props.rpcs;

import lombok.Data;


@Data
public class CmsServerProperties {
    private String protocol = "http";
    private String name = "jbone-cms-server";
}
