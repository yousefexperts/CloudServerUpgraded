package cn.cas.spring.insight.bpm.server;

import cn.cas.spring.insight.bpm.api.ProcessInstanceApi;
import cn.cas.spring.insight.bpm.api.dto.request.ReactiveProcessRequestDTO;
import cn.cas.spring.insight.bpm.api.dto.request.StartProcessRequestDTO;
import cn.cas.spring.insight.bpm.api.dto.request.StopProcessRequestDTO;
import cn.cas.spring.insight.bpm.api.dto.request.SuspendProcessRequestDTO;
import cn.cas.spring.insight.bpm.api.dto.response.StartProcessResponseDTO;
import cn.cas.spring.insight.bpm.core.service.ProcessInstanceService;
import cn.jbone.common.exception.JboneException;
import cn.jbone.common.rpc.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/processInstance")
public class ProcessInstanceApiImpl implements ProcessInstanceApi {

    @Autowired
    private ProcessInstanceService processInstanceService;

    @RequestMapping("start")
    public Result<StartProcessResponseDTO> startProcess(StartProcessRequestDTO startProcessRequestDTO) {
        StartProcessResponseDTO responseDTO = null;
        try {
            responseDTO = processInstanceService.startProcess(startProcessRequestDTO);
        }catch (JboneException e){
            return Result.wrap500Error(e.getMessage());
        }catch (Exception e) {
            return Result.wrap500Error();
        }
        return new Result(responseDTO);
    }

    @RequestMapping("stop")
    public Result<Void> stopProcess(StopProcessRequestDTO stopProcessRequestDTO) {
        try {
            processInstanceService.stopProcess(stopProcessRequestDTO);
        }catch (JboneException e){
            return Result.wrap500Error(e.getMessage());
        }catch (Exception e) {
            return Result.wrap500Error();
        }
        return Result.wrapSuccess();
    }

    @RequestMapping("suspend")
    public Result<Void> suspendProcess(SuspendProcessRequestDTO suspendProcessRequestDTO) {
        try {
            processInstanceService.suspendProcess(suspendProcessRequestDTO);
        }catch (JboneException e){
            return Result.wrap500Error(e.getMessage());
        }catch (Exception e) {
            return Result.wrap500Error();
        }
        return Result.wrapSuccess();
    }

    @RequestMapping("reactive")
    public Result<Void> reactiveProcess(ReactiveProcessRequestDTO reactiveProcessRequestDTO) {
        try {
            processInstanceService.reactiveProcess(reactiveProcessRequestDTO);
        }catch (JboneException e){
            return Result.wrap500Error(e.getMessage());
        }catch (Exception e) {
            return Result.wrap500Error();
        }
        return Result.wrapSuccess();
    }
}
