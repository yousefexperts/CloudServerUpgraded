package cn.cas.spring.insight.bpm.api.dto.request;

import lombok.Data;

import java.util.List;

import cn.cas.spring.insight.bpm.api.enums.MyTaskStatusEnum;

@Data
public class QueryMyTaskRequestDTO {
    private String username;
    private List<MyTaskStatusEnum> status;
    private String processInstanceId;
    private String processDefinitionKey;
}
