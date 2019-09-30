package cn.cas.spring.insight.bpm.api.dto.request;

import lombok.Data;

@Data
public class ClaimTaskRequestDTO {
    private String taskId;
    private String operator;
}
