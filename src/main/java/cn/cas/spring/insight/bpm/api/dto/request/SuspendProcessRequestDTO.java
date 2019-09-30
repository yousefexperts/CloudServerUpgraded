package cn.cas.spring.insight.bpm.api.dto.request;

import lombok.Data;


@Data
public class SuspendProcessRequestDTO {
    private String operator;
    private String processInstanceId;
    private String reason;
}
