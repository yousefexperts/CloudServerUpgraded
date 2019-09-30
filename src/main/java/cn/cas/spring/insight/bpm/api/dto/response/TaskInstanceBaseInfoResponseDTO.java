package cn.cas.spring.insight.bpm.api.dto.response;

import lombok.Data;

import java.util.Date;


@Data
public class TaskInstanceBaseInfoResponseDTO {
    private String id;
    private String name;
    private String processInstanceId;
    private String taskDefinitionKey;
    private int priority;
    private String owner;
    private String assignee;
    private String processDefinitionKey;
    private String processName;
    private Date createTime;
    private Date dueDate;
    private String isSuspended;
}
