package cn.insight.sys.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserResponseDO implements Serializable {
    private UserBaseInfoDO baseInfo;
    private UserAuthInfoDO authInfo;
    private UserSecurityInfoDO securityInfo;
}
