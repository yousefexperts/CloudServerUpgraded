package cn.insight.configuration.props.rpcs;

import lombok.Data;

import java.io.Serializable;

@Data
public class DecorationServerFeignProperties implements Serializable {
    private String protocol = "http";
    private String name = "jbone-b2b2c-decoration-server";
}
