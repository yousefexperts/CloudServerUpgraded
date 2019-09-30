package cn.insight.configuration.props;

import lombok.Data;

import java.io.Serializable;

import cn.insight.configuration.props.rpcs.*;


@Data
public class RpcProperties implements Serializable {

    private SysServerProperties sysServer = new SysServerProperties();
    private CmsServerProperties cmsServer = new CmsServerProperties();
    private EbPortalServerProperties ebPortalServer = new EbPortalServerProperties();
    private ShopServerProperties shopServer = new ShopServerProperties();
    private ItemServerProperties itemServer = new ItemServerProperties();
    private DecorationServerProperties decorationServer = new DecorationServerProperties();

}
