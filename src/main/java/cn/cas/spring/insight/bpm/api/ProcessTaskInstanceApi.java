package cn.cas.spring.insight.bpm.api;

import cn.cas.spring.insight.bpm.api.dto.request.*;
import cn.cas.spring.insight.bpm.api.dto.response.TaskInstanceResponseDTO;
import cn.jbone.common.rpc.Result;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/processTaskInstance")
public interface ProcessTaskInstanceApi {

    /**
     *
     * @param claimTaskRequestDTO
     * @return
     */
    @RequestMapping("/claim")
    Result<TaskInstanceResponseDTO> claimTask(ClaimTaskRequestDTO claimTaskRequestDTO);

    /**
     *
     * @param completeTaskRequestDTO
     * @return
     */
    @RequestMapping("/complete")
    Result<TaskInstanceResponseDTO> completeTask(CompleteTaskRequestDTO completeTaskRequestDTO);

    /**
     *
     * @param claimAndCompleteTaskRequestDTO
     * @return
     */
    @RequestMapping("/claimAndComplete")
    Result<TaskInstanceResponseDTO> claimAndCompleteTask(ClaimAndCompleteTaskRequestDTO claimAndCompleteTaskRequestDTO);

    /**
     *
     * @param suspendTaskRequestDTO
     * @return
     */
    @RequestMapping("/suspend")
    Result<Void> suspendTask(SuspendTaskRequestDTO suspendTaskRequestDTO);

    /**
     *
     * @param reactiveTaskRequestDTO
     * @return
     */
    @RequestMapping("/reactive")
    Result<Void> reactiveTask(ReactiveTaskRequestDTO reactiveTaskRequestDTO);

    /**
     *
     * @param skipTaskRequestDTO
     * @return
     */
    @RequestMapping("/skip")
    Result<Void> skipTask(SkipTaskRequestDTO skipTaskRequestDTO);
}
