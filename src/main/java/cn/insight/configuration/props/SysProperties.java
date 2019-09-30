package cn.insight.configuration.props;

import java.io.Serializable;

public class SysProperties implements Serializable {

    private JdbcProperties jdbc = new JdbcProperties();


    private String serverName;

    public JdbcProperties getJdbc() {
        return jdbc;
    }

    public void setJdbc(JdbcProperties jdbc) {
        this.jdbc = jdbc;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String toString() {
        return "SysProperties{" +
                "jdbc=" + jdbc +
                ", serverName='" + serverName + '\'' +
                '}';
    }
}
