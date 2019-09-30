package cn.cas.spring.insight.bpm.api;

import cn.cas.spring.insight.bpm.api.dto.response.ProcessInstanceQueryRequestDTO;
import cn.cas.spring.insight.bpm.api.dto.response.ProcessInstanceQueryResponseDTO;
import cn.jbone.common.rpc.Result;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/processQuery")
public interface ProcessQueryApi {

    /**
     *
     * @param processInstanceQueryRequestDTO
     * @return
     */
    @RequestMapping("/queryProcessInstance")
    Result<ProcessInstanceQueryResponseDTO> queryProcessInstance(ProcessInstanceQueryRequestDTO processInstanceQueryRequestDTO);
}
