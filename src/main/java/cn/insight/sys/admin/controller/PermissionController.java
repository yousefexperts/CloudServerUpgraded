package cn.insight.sys.admin.controller;

import cn.insight.sys.core.dao.domain.RbacPermissionEntity;
import cn.insight.sys.core.dao.domain.RbacSystemEntity;
import cn.insight.sys.core.service.MenuService;
import cn.insight.sys.core.service.PermissionService;
import cn.insight.sys.core.service.SystemService;
import cn.insight.sys.core.service.model.ListModel;
import cn.insight.sys.core.service.model.menu.TreeMenuModel;
import cn.insight.sys.core.service.model.permission.PermissionBaseInfoModel;
import cn.insight.sys.core.service.model.permission.PermissionCreateModel;
import cn.insight.sys.core.service.model.permission.PermissionUpdateModel;
import cn.jbone.common.ui.result.Result;
import cn.jbone.common.utils.ResultUtils;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import java.util.List;


@Controller
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private MenuService menuService;

    @Description("index")
    @RequiresPermissions("sys:permission:read")
    @RequestMapping("/index")
    public String index(){
        return "pages/permission/index";
    }

    @Description("list")
    @RequiresPermissions("sys:permission:read")
    @RequestMapping("/list")
    @ResponseBody
    public Result list(ListModel listModel){
        PageRequest pageRequest = new PageRequest(listModel.getPageNumber()-1,listModel.getPageSize(), Sort.Direction.fromString(listModel.getSortOrder()),listModel.getSortName());
        //分页查找
        Page<RbacPermissionEntity> page = permissionService.findPage(listModel.getSearchText(),pageRequest);
        List<PermissionBaseInfoModel> list = permissionService.getBaseInfos(page.getContent());
        return ResultUtils.wrapSuccess(page.getTotalElements(),list);
    }

    @RequiresPermissions("sys:permission:create")
    @Description("toCreate")
    @RequestMapping("/toCreate")
    public String toCreate(ModelMap modelMap){
        List<RbacSystemEntity> systemEntities = systemService.findAll();
        modelMap.put("systemList",systemEntities);
        return "pages/permission/create";
    }

    @RequiresPermissions("sys:permission:create")
    @Description("create")
    @RequestMapping("/create")
    @ResponseBody
    public Result create(@Validated PermissionCreateModel createModel, BindingResult bindingResult){
        permissionService.save(createModel);
        return ResultUtils.wrapSuccess();
    }

    @RequiresPermissions("sys:permission:update")
    @Description("toUpdate")
    @RequestMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable("id")int id, ModelMap model){
        PermissionBaseInfoModel permissionBaseInfoModel = permissionService.getBaseInfo(id);
        List<RbacSystemEntity> systemEntities = systemService.findAll();
        model.put("systemList",systemEntities);
        model.put("permission",permissionBaseInfoModel);
        if(permissionBaseInfoModel.getMenuId() != 0){
            TreeMenuModel menuModel = menuService.get(permissionBaseInfoModel.getMenuId());
            model.put("menuModel",menuModel);
        }
        return "pages/permission/update";
    }

    @RequiresPermissions("sys:permission:update")
    @Description("update")
    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Validated PermissionUpdateModel updateModel, BindingResult bindingResult){
        permissionService.update(updateModel);
        return ResultUtils.wrapSuccess();
    }

    @RequiresPermissions("sys:permission:delete")
    @Description("delete")
    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public Result delete(@PathVariable("ids")String ids){
        permissionService.delete(ids);
        return ResultUtils.wrapSuccess();
    }

    @RequiresPermissions("sys:permission:read")
    @Description("get")
    @RequestMapping("/get/{id}")
    @ResponseBody
    public Result get(@PathVariable("id")int id){
        return ResultUtils.wrapSuccess(permissionService.getBaseInfo(id));
    }

    @RequiresPermissions("sys:permission:read")
    @Description("getPermissions")
    @RequestMapping("/getPermissions/{systemId}/{menuId}")
    @ResponseBody
    public Result getPermissions(@PathVariable("systemId")int systemId,@PathVariable("menuId")int menuId){
        return ResultUtils.wrapSuccess(permissionService.getPermissions(systemId,menuId));
    }
}
