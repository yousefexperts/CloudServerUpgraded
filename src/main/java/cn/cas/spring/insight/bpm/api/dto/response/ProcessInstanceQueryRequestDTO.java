package cn.cas.spring.insight.bpm.api.dto.response;

import lombok.Data;

@Data
public class ProcessInstanceQueryRequestDTO {

    private String processInstanceId;
    private String owner;
}
