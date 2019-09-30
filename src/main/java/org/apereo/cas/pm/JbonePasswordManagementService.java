package org.apereo.cas.pm;

import cn.insight.sys.api.UserApi;
import cn.insight.sys.common.dto.request.ChangePasswordRequestDTO;
import cn.insight.sys.common.dto.response.UserBaseInfoResponseDTO;
import cn.insight.sys.common.dto.response.UserSecurityQuestionsResponseDTO;
import cn.jbone.common.rpc.Result;

import org.apereo.cas.CipherExecutor;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.configuration.model.support.pm.PasswordManagementProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class JbonePasswordManagementService extends BasePasswordManagementService {

    private Logger logger = LoggerFactory.getLogger(JbonePasswordManagementService.class);

    private UserApi userApi;

    public JbonePasswordManagementService(CipherExecutor<Serializable, String> cipherExecutor, String issuer, PasswordManagementProperties properties,UserApi userApi) {
        super(properties,cipherExecutor, issuer);
        this.userApi = userApi;
    }

    @Override
    public String findEmail(String username) {
        Result<UserBaseInfoResponseDTO> userModelResult = userApi.getUserByName(username);
        if(userModelResult.isSuccess() && userModelResult.getData() != null){
            return userModelResult.getData().getEmail();
        }
        return null;
    }

    @Override
    public Map<String, String> getSecurityQuestions(String username) {
        Map<String, String> securityQuestions = new LinkedHashMap<>();

        Result<List<UserSecurityQuestionsResponseDTO>> securityQuestionsResult = userApi.getUserSecurityQuestions(username);
        if(securityQuestionsResult.isSuccess() && securityQuestionsResult.getData()!=null){
            for (UserSecurityQuestionsResponseDTO responseDTO : securityQuestionsResult.getData()){
                securityQuestions.put(responseDTO.getQuestion(),responseDTO.getAnswer());
            }
        }
        return securityQuestions;
    }


    @Override
    public boolean changeInternal(Credential c, PasswordChangeBean bean) throws InvalidPasswordException {
        ChangePasswordRequestDTO requestDTO = new ChangePasswordRequestDTO();
        requestDTO.setUsername(c.getId());
        requestDTO.setPassword(bean.getPassword());
        requestDTO.setConfirmedPassword(bean.getConfirmedPassword());
        Result<Void> result = userApi.changePassword(requestDTO);
        return result.isSuccess();
    }
}
