package cn.insight.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import cn.insight.configuration.props.CasProperties;
import cn.insight.configuration.props.RpcProperties;
import cn.insight.configuration.props.SysProperties;

import javax.annotation.PostConstruct;


@Configuration
@ConfigurationProperties(prefix = "jbone")
public class JboneConfiguration {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private SysProperties sys = new SysProperties();


    private CasProperties cas = new CasProperties();


    private RpcProperties rpc = new RpcProperties();


    public SysProperties getSys() {
        return sys;
    }

    public void setSys(SysProperties sys) {
        this.sys = sys;
    }

    public CasProperties getCas() {
        return cas;
    }

    public void setCas(CasProperties cas) {
        this.cas = cas;
    }

    public RpcProperties getRpc() {
        return rpc;
    }

    public void setRpc(RpcProperties rpc) {
        this.rpc = rpc;
    }

    @Override
    public String toString() {
        return "JboneConfiguration{" +
                "sys=" + sys +
                ", cas=" + cas +
                ", rpc=" + rpc +
                '}';
    }

    @PostConstruct
    public void initProperty(){
        logger.info(this.toString());
    }
}
