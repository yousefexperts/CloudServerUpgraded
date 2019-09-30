package cn.cas.spring.insight.bpm.api.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;


@Data
public class StartProcessRequestDTO implements Serializable {

    private String processDefinitionKey;
    private String operator;
    private String formId;
    private Map<String, Object> variables;
}
