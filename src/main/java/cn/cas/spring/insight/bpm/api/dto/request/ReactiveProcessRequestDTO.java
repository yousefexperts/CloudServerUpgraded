package cn.cas.spring.insight.bpm.api.dto.request;

import lombok.Data;

/**
 * 挂起流程DTO
 */
@Data
public class ReactiveProcessRequestDTO {
    private String operator;
    private String processInstanceId;
    private String reason;
}
