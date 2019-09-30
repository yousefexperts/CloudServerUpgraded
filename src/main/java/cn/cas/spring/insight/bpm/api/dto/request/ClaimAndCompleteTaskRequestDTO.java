package cn.cas.spring.insight.bpm.api.dto.request;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.cas.spring.insight.bpm.api.enums.TaskAuditEnum;


@Data
public class ClaimAndCompleteTaskRequestDTO {
    private String processInstanceId;
    private String taskId;
    private String taskDefinitionKey;
    private TaskAuditEnum audit;
    private String reason;
    private String selectUser;
    private String operator;
    private int priority;
    private String isOverDue;
    private Date validDate;
    private Map<String,Object> variables = new HashMap<>();
}
