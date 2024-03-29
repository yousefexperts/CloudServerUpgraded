package cn.insight.sys.core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.insight.sys.core.dao.domain.RbacRoleEntity;

import java.util.List;

@Repository
public interface RbacRoleRepository extends JpaRepository<RbacRoleEntity,Integer>,JpaSpecificationExecutor<RbacRoleEntity> {
    List<RbacRoleEntity> findByIdIn(int[] ids);

    List<RbacRoleEntity> findByName(String name);
}
