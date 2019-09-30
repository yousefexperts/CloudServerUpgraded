package cn.cas.spring.insight.bpm.api.dto.request;

import lombok.Data;


@Data
public class SuspendTaskRequestDTO {
    private String taskId;
    private String operator;
}
