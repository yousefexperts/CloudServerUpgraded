package cn.cas.spring.insight.bpm.core.service;

import cn.cas.spring.insight.bpm.api.dto.request.ReactiveProcessRequestDTO;
import cn.cas.spring.insight.bpm.api.dto.request.StartProcessRequestDTO;
import cn.cas.spring.insight.bpm.api.dto.request.StopProcessRequestDTO;
import cn.cas.spring.insight.bpm.api.dto.request.SuspendProcessRequestDTO;
import cn.cas.spring.insight.bpm.api.dto.response.StartProcessResponseDTO;
import cn.cas.spring.insight.bpm.core.service.vo.processinstance.ProcessInstanceListVo;
import cn.cas.spring.insight.bpm.core.service.vo.processinstance.ProcessInstanceVo;
import cn.jbone.common.exception.JboneException;
import cn.jbone.common.ui.result.ListResult;
import cn.jbone.common.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.common.api.query.QueryProperty;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessInstanceService {
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IdentityService identityService;

    /**
     *
     * @param processInstanceListVo
     * @return
     */
    public ListResult list(ProcessInstanceListVo processInstanceListVo){
        HistoricProcessInstanceQuery processInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        if(!StringUtils.isBlank(processInstanceListVo.getProcessInstanceId())){
            processInstanceQuery.processInstanceId(processInstanceListVo.getProcessInstanceId());
        }
        if(!StringUtils.isBlank(processInstanceListVo.getOwner())){
            processInstanceQuery.startedBy(processInstanceListVo.getOwner());
        }
        if(!StringUtils.isBlank(processInstanceListVo.getProcessDefinitionKey())){
            processInstanceQuery.processDefinitionKey(processInstanceListVo.getProcessDefinitionKey());
        }
        if(!StringUtils.isBlank(processInstanceListVo.getStatus())){
            if(processInstanceListVo.getStatus().equalsIgnoreCase(ProcessInstanceListVo.ACTIVE_STATUS)){
                processInstanceQuery.unfinished();
            }else if(processInstanceListVo.getStatus().equalsIgnoreCase(ProcessInstanceListVo.STOP_STATUS)){
                processInstanceQuery.finished();
            }
        }
        if(processInstanceListVo.getStartedAfter() != null){
            processInstanceQuery.startedAfter(processInstanceListVo.getStartedAfter());
        }
        if(processInstanceListVo.getStartedBefore() != null){
            processInstanceQuery.startedBefore(processInstanceListVo.getStartedBefore());
        }
        if(processInstanceListVo.getEndAfter() != null){
            processInstanceQuery.finishedAfter(processInstanceListVo.getEndAfter());
        }
        if(processInstanceListVo.getEndBefore() != null){
            processInstanceQuery.finishedBefore(processInstanceListVo.getEndBefore());
        }
        if(processInstanceListVo.getOrderBy() != null){
            processInstanceQuery.orderBy(new QueryProperty() {
                @Override
                public String getName() {
                    return processInstanceListVo.getOrderBy();
                }
            });
            if(!StringUtils.isBlank(processInstanceListVo.getSort()) && processInstanceListVo.getSort().equalsIgnoreCase(ProcessInstanceListVo.SORT_DESC)){
                processInstanceQuery.desc();
            }else{
                processInstanceQuery.asc();
            }
        }
        long count = processInstanceQuery.count();
        List<HistoricProcessInstance> list = processInstanceQuery.list();
        List<ProcessInstanceVo> processInstanceVoList = new ArrayList<>();
        if(list != null && !list.isEmpty()){
            for (HistoricProcessInstance processInstance:list){
                ProcessInstanceVo vo = new ProcessInstanceVo();
                vo.setBusinessKey(processInstance.getBusinessKey());
                vo.setEndTime(processInstance.getEndTime());
                vo.setOwner(processInstance.getStartUserId());
                vo.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
                vo.setProcessInstanceId(processInstance.getId());
                vo.setProcessInstanceName(processInstance.getName());
                vo.setStartTime(processInstance.getStartTime());
                processInstanceVoList.add(vo);
            }
        }
        return ResultUtils.wrapSuccess(count,processInstanceVoList);
    }

    /**
     *
     * @param startProcessRequestDTO
     * @return
     */
    public StartProcessResponseDTO startProcess(StartProcessRequestDTO startProcessRequestDTO) {
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(startProcessRequestDTO.getProcessDefinitionKey()).latestVersion()
                .singleResult();

        if (processDefinition == null) {
            throw new JboneException("JboneException[" + startProcessRequestDTO.getProcessDefinitionKey() + "]JboneException");
        }
        if (processDefinition.isSuspended()) {
            throw new JboneException("JboneException[" + startProcessRequestDTO.getProcessDefinitionKey() + "]JboneException");
        }
        identityService.setAuthenticatedUserId(startProcessRequestDTO.getOperator());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(startProcessRequestDTO.getProcessDefinitionKey(),startProcessRequestDTO.getFormId(),startProcessRequestDTO.getVariables());
        if(processInstance == null){
            throw new JboneException("JboneException。");
        }

        StartProcessResponseDTO responseDTO = new StartProcessResponseDTO();
        responseDTO.setProcessInstanceId(processInstance.getProcessInstanceId());
        responseDTO.setProcessName(processInstance.getName());
        responseDTO.setStartTime(processInstance.getStartTime());
        responseDTO.setOwner(processInstance.getStartUserId());
        responseDTO.setBusinessKey(processInstance.getBusinessKey());
        return responseDTO;
    }

    public void stopProcess(StopProcessRequestDTO stopProcessRequestDTO){
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(stopProcessRequestDTO.getProcessInstanceId()).singleResult();
        if(processInstance == null){
            throw new JboneException("JboneException。");
        }

        identityService.setAuthenticatedUserId(stopProcessRequestDTO.getOperator());

        runtimeService.deleteProcessInstance(stopProcessRequestDTO.getProcessInstanceId(),stopProcessRequestDTO.getReason());
    }

    public void suspendProcess(SuspendProcessRequestDTO suspendProcessRequestDTO){
        historyService.createHistoricDetailQuery().processInstanceId("").singleResult();
    }

    public void reactiveProcess(ReactiveProcessRequestDTO reactiveProcessRequestDTO){

    }
}
