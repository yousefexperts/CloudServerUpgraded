package cn.insight.sys.admin.controller;

import cn.insight.sys.core.dao.domain.*;
import cn.insight.sys.core.service.PermissionService;
import cn.insight.sys.core.service.RoleService;
import cn.insight.sys.core.service.SystemService;
import cn.insight.sys.core.service.UserService;
import cn.insight.sys.core.service.model.ListModel;
import cn.insight.sys.core.service.model.common.AssignPermissionModel;
import cn.insight.sys.core.service.model.user.*;
import cn.jbone.common.ui.result.Result;
import cn.jbone.common.utils.ResultUtils;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private PermissionService permissionService;

    @Description("index")
    @RequiresPermissions("sys:user:read")
    @RequestMapping("/index")
    public String index(){
        return "pages/user/index";
    }

    @Description("list")
    @RequiresPermissions("sys:user:read")
    @RequestMapping("/list")
    @ResponseBody
    public Result list(ListModel listModel){
        PageRequest pageRequest = new PageRequest(listModel.getPageNumber()-1,listModel.getPageSize(), Sort.Direction.fromString(listModel.getSortOrder()),listModel.getSortName());
        Page<RbacUserEntity> page = userService.findPage(listModel.getSearchText(),pageRequest);
        List<UserBaseInfoModel> list = userService.getUserBaseInfos(page.getContent());
        return ResultUtils.wrapSuccess(page.getTotalElements(),list);
    }

    @RequiresPermissions("sys:user:create")
    @Description("toCreate")
    @RequestMapping("/toCreate")
    public String toCreate(){
        return "pages/user/create";
    }

    @RequiresPermissions("sys:user:create")
    @Description("create")
    @RequestMapping("/create")
    @ResponseBody
    public Result create(@Validated CreateUserModel userModel, BindingResult bindingResult){
        userService.save(userModel);
        return ResultUtils.wrapSuccess();
    }

    @RequiresPermissions("sys:user:update")
    @Description("toUpdate")
    @RequestMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable("id")String id, ModelMap model){
        RbacUserEntity userEntity = userService.findById(Integer.parseInt(id));
        UpdateUserModel userModel = new UpdateUserModel();
        BeanUtils.copyProperties(userEntity,userModel);
        model.put("userEntity",userModel);
        return "pages/user/update";
    }

    @RequiresPermissions("sys:user:update")
    @Description("update")
    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Validated UpdateUserModel userModel, BindingResult bindingResult){
        userService.update(userModel);
        return ResultUtils.wrapSuccess();
    }

    @RequiresPermissions("sys:user:delete")
    @Description("delete")
    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public Result delete(@PathVariable("ids")String ids){
        userService.delete(ids);
        return ResultUtils.wrapSuccess();
    }

    @RequiresPermissions("sys:user:read")
    @Description("get")
    @RequestMapping("/get/{id}")
    @ResponseBody
    public Result get(@PathVariable("id")String id){
        RbacUserEntity userEntity = userService.findById(Integer.parseInt(id));
        return ResultUtils.wrapSuccess(userEntity);
    }

    @RequiresPermissions("sys:user:assignRole")
    @Description("toAssignRole")
    @RequestMapping("/toAssignRole/{id}")
    public String toAssignRole(@PathVariable("id")String id,ModelMap modelMap){
        RbacUserEntity userEntity = userService.findById(Integer.parseInt(id));
        modelMap.put("userRoles",roleService.getSimpleModels(userEntity.getRoles()));
        modelMap.put("allRoles",roleService.getSimpleModels(roleService.findAll()));
        modelMap.put("userId",id);
        return "pages/user/assignRole";
    }

    @RequiresPermissions("sys:user:assignRole")
    @Description("doAssignRole")
    @RequestMapping("/doAssignRole")
    @ResponseBody
    public Result doAssignRole(AssignRoleModel assignRoleModel){
        userService.assignRole(assignRoleModel);
        return ResultUtils.wrapSuccess();
    }

    @RequiresPermissions("sys:user:assignMenu")
    @Description("toAssignMenu")
    @RequestMapping("toAssignMenu/{userId}")
    public String toAssignMenu(@PathVariable("userId")String userId,ModelMap modelMap){
        List<RbacSystemEntity> systemEntities = systemService.findAll();
        modelMap.put("systemList",systemEntities);
        modelMap.put("userId",userId);

        RbacUserEntity userEntity = userService.findById(Integer.parseInt(userId));
        List<RbacMenuEntity> menuEntityList = userEntity.getMenus();
        List<Integer> menuIds = new ArrayList<>();
        if(menuEntityList != null && !menuEntityList.isEmpty()){
            for (RbacMenuEntity menuEntity : menuEntityList){
                menuIds.add(menuEntity.getId());
            }
        }
        modelMap.put("menuIds",menuIds);

        return "pages/user/assignMenu";
    }

    @RequiresPermissions("sys:user:assignMenu")
    @Description("doAssignMenu")
    @RequestMapping("doAssignMenu")
    @ResponseBody
    public Result doAssignMenu(@Validated AssignMenuModel menuModel, BindingResult bindingResult){
        userService.assignMenu(menuModel);
        return ResultUtils.wrapSuccess();
    }

    @RequiresPermissions("sys:user:assignOrganization")
    @Description("toOrganization")
    @RequestMapping("toAssignOrganization/{userId}")
    public String toOrganization(@PathVariable("userId")String userId,ModelMap modelMap){
        modelMap.put("userId",userId);

        RbacUserEntity userEntity = userService.findById(Integer.parseInt(userId));
        List<RbacOrganizationEntity> organizationEntities = userEntity.getOrganizations();
        List<Integer> organizationIds = new ArrayList<>();
        if(organizationEntities != null && !organizationEntities.isEmpty()){
            for (RbacOrganizationEntity organizationEntity : organizationEntities){
                organizationIds.add(organizationEntity.getId());
            }
        }
        modelMap.put("organizationIds",organizationIds);

        return "pages/user/assignOrganization";
    }

    @RequiresPermissions("sys:user:assignOrganization")
    @Description("doAssignOrganization")
    @RequestMapping("doAssignOrganization")
    @ResponseBody
    public Result doAssignOrganization(@Validated AssignOrganizationModel organizationModel, BindingResult bindingResult){
        userService.assignOrganization(organizationModel);
        return ResultUtils.wrapSuccess();
    }

    @RequiresPermissions("sys:user:assignPermission")
    @Description("toAssignPermission")
    @RequestMapping("toAssignPermission/{userId}")
    public String toAssignPermission(@PathVariable("userId")String userId,ModelMap modelMap){
        List<RbacSystemEntity> systemEntities = systemService.findAll();
        modelMap.put("systemList",systemEntities);
        modelMap.put("id",userId);

        RbacUserEntity userEntity = userService.findById(Integer.parseInt(userId));
        List<RbacPermissionEntity> permissions = userEntity.getPermissions();

        modelMap.put("permissions",permissionService.getBaseInfos(permissions));

        modelMap.put("commitUrl", "/user/doAssignPermission");

        return "pages/common/assignPermission";
    }

    @RequiresPermissions("sys:user:assignPermission")
    @Description("doAssignPermission")
    @RequestMapping("doAssignPermission")
    @ResponseBody
    public Result doAssignPermission(@Validated AssignPermissionModel assignPermissionModel, BindingResult bindingResult){
        userService.assignPermission(assignPermissionModel);
        return ResultUtils.wrapSuccess();
    }

    @RequiresPermissions("sys:user:modifyPassword")
    @Description("toModifyPassword")
    @RequestMapping("/toModifyPassword/{userId}")
    public String toModifyPassword(@PathVariable("userId")String userId, ModelMap modelMap){
        modelMap.put("userId",userId);
        return "pages/user/modifyPassword";
    }

    @RequiresPermissions("sys:user:modifyPassword")
    @Description("doModifyPassword")
    @RequestMapping("/doModifyPassword")
    @ResponseBody
    public Result doModifyPassword(@Validated ModifyPasswordModel modifyPasswordModel, BindingResult bindingResult){
        userService.modifyPassword(modifyPasswordModel);
        return ResultUtils.wrapSuccess();
    }
}
