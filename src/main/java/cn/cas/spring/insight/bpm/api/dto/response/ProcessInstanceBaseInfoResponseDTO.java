package cn.cas.spring.insight.bpm.api.dto.response;

import lombok.Data;

import java.util.Date;

import cn.cas.spring.insight.bpm.api.enums.ProcessInstanceStatusEnum;


@Data
public class ProcessInstanceBaseInfoResponseDTO {

    private String id;
    private String name;
    private String definitionKey;
    private String owner;
    private Date createTime;
    private Date dueDate;
    private Date closeTime;
    private ProcessInstanceStatusEnum status;
}

