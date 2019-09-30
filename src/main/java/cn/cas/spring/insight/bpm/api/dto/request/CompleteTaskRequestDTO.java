package cn.cas.spring.insight.bpm.api.dto.request;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Data
public class CompleteTaskRequestDTO {
    private String processInstanceId;
    private String taskId;
    private String taskDefinitionKey;
    private String isAgree;
    private String reason;
    private String selectUser;
    private String operator;
    private int priority;
    private String isOverDue;
    private Date validDate;
    private Map<String,Object> variables = new HashMap<>();
}
