package cn.insight.sys.core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.insight.sys.core.dao.domain.SysLogEntity;

@Repository
public interface SysLogRepository extends JpaRepository<SysLogEntity,Integer>,JpaSpecificationExecutor<SysLogEntity>{
}
