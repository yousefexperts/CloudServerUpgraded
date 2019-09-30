package cn.cas.spring.insight.bpm.api.dto.request;

import lombok.Data;

import java.util.Map;


@Data
public class SkipTaskRequestDTO {

    private String taskId;
    private String targetNode;
    private String reason;
    private String operator;
    private Map<String, Object> variables;
}
