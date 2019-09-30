package cn.insight.sys.common.dto.request;

import cn.insight.sys.common.dto.ThirdPartyName;
import lombok.Data;

@Data
public class ThirdPartyUserLoginRequestDTO {
    private String id;

    private ThirdPartyName thirdPartyName;

}
