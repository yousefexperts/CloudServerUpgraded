package cn.cas.spring.insight.bpm.core.service.vo.processinstance;

import lombok.Data;

import java.util.Date;

@Data
public class ProcessInstanceListVo {
    private String processInstanceId;
    private String owner;
    private String processDefinitionKey;
    private String status;
    private String processInstanceName;
    private Date startedBefore;
    private Date startedAfter;
    private Date endBefore;
    private Date endAfter;
    private String orderBy;
    private String sort;


    public static final String ACTIVE_STATUS = "active";
    public static final String STOP_STATUS = "stop";
    public static final String SORT_ASC = "asc";
    public static final String SORT_DESC = "desc";
}
