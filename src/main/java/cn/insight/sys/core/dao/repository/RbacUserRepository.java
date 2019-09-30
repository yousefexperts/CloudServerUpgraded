package cn.insight.sys.core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.insight.sys.core.dao.domain.RbacUserEntity;

@Repository
public interface RbacUserRepository extends JpaRepository<RbacUserEntity,Integer>,JpaSpecificationExecutor<RbacUserEntity>{
    RbacUserEntity findByUsername(String username);
}
