package cn.insight.sys.core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.insight.sys.core.dao.domain.RbacUserRoleEntity;

@Repository
public interface RbacUserRoleRepository extends JpaRepository<RbacUserRoleEntity,Integer>,JpaSpecificationExecutor<RbacUserRoleEntity> {
    void deleteByUserId(int userId);
}
