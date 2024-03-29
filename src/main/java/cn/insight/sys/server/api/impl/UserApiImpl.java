package cn.insight.sys.server.api.impl;

import cn.insight.sys.api.UserApi;
import cn.insight.sys.common.UserRequestDO;
import cn.insight.sys.common.UserResponseDO;
import cn.insight.sys.common.dto.request.ChangePasswordRequestDTO;
import cn.insight.sys.common.dto.request.GithubUserLoginRequestDTO;
import cn.insight.sys.common.dto.response.UserBaseInfoResponseDTO;
import cn.insight.sys.common.dto.response.UserInfoResponseDTO;
import cn.insight.sys.common.dto.response.UserSecurityQuestionsResponseDTO;
import cn.insight.sys.core.service.UserService;
import cn.insight.sys.core.service.model.user.UserBaseInfoModel;
import cn.jbone.common.rpc.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserApiImpl implements UserApi {

    private Logger logger = LoggerFactory.getLogger(UserApiImpl.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/getUserByName")
    public Result<UserBaseInfoResponseDTO> getUserByName(String username) {
        UserBaseInfoResponseDTO userInfoModel = null;
        try {
            UserBaseInfoModel userEntity = userService.findByUserName(username);

            if(userEntity == null){
                return Result.wrap404Error("user is not found");
            }

            userInfoModel = new UserBaseInfoResponseDTO();

            BeanUtils.copyProperties(userEntity,userInfoModel);

        } catch (Exception e) {
            return Result.wrap500Error(e.getMessage());
        }
        return new Result(userInfoModel);
    }

    @RequestMapping("/getUserDetail")
    public Result<UserInfoResponseDTO> getUserDetailByName(String username){
        UserInfoResponseDTO userModel = null;
        try {
            userModel = userService.getUserDetailByNameAndServerName(username,null);
            if(userModel == null){
                return Result.wrap404Error("user is not found");
            }
        } catch (Exception e) {
            return Result.wrap500Error(e.getMessage());
        }

        return new Result(userModel);
    }


    @RequestMapping("/getUserDetailByNameAndServerName")
    public Result<UserInfoResponseDTO> getUserDetailByNameAndServerName(String username, String serverName) {
        UserInfoResponseDTO userModel = null;
        try {
            userModel = userService.getUserDetailByNameAndServerName(username,serverName);
            if(userModel == null){
                return Result.wrap404Error("user is not found");
            }
        } catch (Exception e) {
            return Result.wrap500Error(e.getMessage());
        }
        return new Result(userModel);
    }

    @RequestMapping("/getUserSecurityQuestions")
    public Result<List<UserSecurityQuestionsResponseDTO>> getUserSecurityQuestions(String username) {
        List<UserSecurityQuestionsResponseDTO> responseDTOList = null;
        try {
            responseDTOList = userService.findUserSecurityQuestions(username);
            if(responseDTOList == null){
                return Result.wrap404Error("securityQuestions is not found");
            }
        } catch (Exception e) {
            logger.warn("getUserSecurityQuestions error.",e);
            return Result.wrap500Error(e.getMessage());
        }
        return new Result(responseDTOList);
    }

    @Override
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<Void> changePassword(@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) {
        try {
            userService.modifyPassword(changePasswordRequestDTO);
        } catch (Exception e) {
            logger.warn("modify password error.",e);
            return Result.wrap500Error(e.getMessage());
        }
        return new Result();
    }

    @Override
    @RequestMapping(value = "/thirdPartyUserLogin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<Void> thirdPartyUserLogin(@RequestBody GithubUserLoginRequestDTO githubUserLoginRequestDTO) {
        try {
            userService.thirdPartyLogin(githubUserLoginRequestDTO);
        } catch (Exception e) {
            logger.warn("third party login error.",e);
            return Result.wrap500Error(e.getMessage());
        }
        return new Result();
    }

    @Override
    @RequestMapping(value = "/commonRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result<UserResponseDO> commonRequest(@RequestBody UserRequestDO userRequestDO) {
        UserResponseDO userResponseDO = null;
        try {
            userResponseDO = userService.commonRequest(userRequestDO);
            if(userResponseDO == null){
                return Result.wrap404Error("user is not found");
            }
        } catch (NoSuchElementException e) {
            return Result.wrap404Error("user is not found");
        } catch(Exception e){
            return Result.wrap500Error(e.getMessage());
        }
        return new Result(userResponseDO);
    }
}
