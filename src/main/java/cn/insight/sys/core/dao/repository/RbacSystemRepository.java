package cn.insight.sys.core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.insight.sys.core.dao.domain.RbacSystemEntity;

import java.util.List;

@Repository
public interface RbacSystemRepository extends JpaRepository<RbacSystemEntity,Integer>,JpaSpecificationExecutor<RbacSystemEntity> {
    List<RbacSystemEntity> findByServiceCasFilterOrderByServiceEvaluationOrderDesc(String serviceCasFilter);
    RbacSystemEntity findByName(String name);
}
