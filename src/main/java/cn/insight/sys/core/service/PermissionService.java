package cn.insight.sys.core.service;

import cn.insight.sys.core.dao.domain.RbacPermissionEntity;
import cn.insight.sys.core.dao.repository.RbacPermissionRepository;
import cn.insight.sys.core.service.model.permission.PermissionBaseInfoModel;
import cn.insight.sys.core.service.model.permission.PermissionCreateModel;
import cn.insight.sys.core.service.model.permission.PermissionUpdateModel;
import cn.jbone.common.exception.JboneException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class PermissionService {

    @Autowired
    RbacPermissionRepository permissionRepository;

    public void save(PermissionCreateModel createModel){
        RbacPermissionEntity permissionEntity = new RbacPermissionEntity();
        BeanUtils.copyProperties(createModel,permissionEntity);
        permissionRepository.save(permissionEntity);
    }
    public void update(PermissionUpdateModel updateModel){
        RbacPermissionEntity permissionEntity = permissionRepository.getOne(updateModel.getId());
        if(permissionEntity == null){
            throw new JboneException("JboneException");
        }
        BeanUtils.copyProperties(updateModel,permissionEntity);
        permissionRepository.save(permissionEntity);
    }

    public void delete(String ids){
        String[] idArray =  ids.split(",");
        for (String id:idArray){
            if(StringUtils.isBlank(id)){
                continue;
            }
            permissionRepository.deleteById(Integer.parseInt(id));
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public RbacPermissionEntity findById(int id){
        RbacPermissionEntity permissionEntity = permissionRepository.getOne(id);
        if(permissionEntity == null){
            throw new JboneException("JboneException");
        }
        return permissionEntity;
    }


    /**
     *
     * @param condition
     * @param pageRequest
     * @return
     */
    public Page<RbacPermissionEntity> findPage(String condition,PageRequest pageRequest){
        return permissionRepository.findAll(new PermissionSpecification(condition),pageRequest);
    }

    /**
     *
     * @param systemId
     * @param menuId
     * @return
     */
    public List<PermissionBaseInfoModel> getPermissions(int systemId, int menuId){
        List<RbacPermissionEntity> permissionEntities = null;
        if(menuId != 0){
            permissionEntities = permissionRepository.findBySystemIdAndMenuId(systemId,menuId);
        }else{
            permissionEntities = permissionRepository.findBySystemId(systemId);
        }
        return getBaseInfos(permissionEntities);
    }


    private class PermissionSpecification implements Specification<RbacPermissionEntity> {
        private String condition;
        public PermissionSpecification(String condition){
            this.condition = condition;
        }
        @Override
        public Predicate toPredicate(Root<RbacPermissionEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            if(StringUtils.isBlank(condition)){
                return criteriaQuery.getRestriction();
            }
            Path<String> name = root.get("name");
            Path<String> permissionValue = root.get("permissionValue");
            Predicate predicate = criteriaBuilder.or(criteriaBuilder.like(name,"%" + condition + "%"),criteriaBuilder.like(permissionValue,"%" + condition + "%"));
            return predicate;
        }
    }

    public List<PermissionBaseInfoModel> getBaseInfos(List<RbacPermissionEntity> permissionEntities){
        List<PermissionBaseInfoModel> permissionBaseInfoModels = new ArrayList<>();
        if(permissionEntities == null || permissionEntities.isEmpty()){
            return permissionBaseInfoModels;
        }
        for (RbacPermissionEntity permissionEntity : permissionEntities){
            PermissionBaseInfoModel baseInfoModel = new PermissionBaseInfoModel();
            BeanUtils.copyProperties(permissionEntity,baseInfoModel);
            permissionBaseInfoModels.add(baseInfoModel);
        }
        return permissionBaseInfoModels;
    }

    public PermissionBaseInfoModel getBaseInfo(RbacPermissionEntity permissionEntity){
        PermissionBaseInfoModel baseInfoModel = new PermissionBaseInfoModel();
        BeanUtils.copyProperties(permissionEntity,baseInfoModel);
        return baseInfoModel;
    }

    public PermissionBaseInfoModel getBaseInfo(int id){
        RbacPermissionEntity permissionEntity = this.findById(id);
        PermissionBaseInfoModel baseInfoModel = new PermissionBaseInfoModel();
        BeanUtils.copyProperties(permissionEntity,baseInfoModel);
        return baseInfoModel;
    }

}
