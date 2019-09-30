package cn.cas.spring.insight.bpm.api.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class StartProcessResponseDTO implements Serializable {

    private String processInstanceId;
    private String processName;
    private Date startTime;
    private String owner;
    private String businessKey;

}
