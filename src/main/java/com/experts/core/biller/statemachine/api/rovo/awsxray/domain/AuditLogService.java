package com.experts.core.biller.statemachine.api.rovo.awsxray.domain;

import com.experts.core.biller.statemachine.api.rovo.awsxray.domain.entities.jpa.AuditLogEntity;
import com.experts.core.biller.statemachine.api.rovo.awsxray.domain.entities.jpa.BaseJpaEntity;
import com.experts.core.biller.statemachine.api.rovo.awsxray.domain.entities.jpa.LogUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;

@Transactional(transactionManager = "dbTransactionManager")
public class AuditLogService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @PersistenceContext(unitName = "EXPERTS-MYSQL")
    protected EntityManager entityManager;

    public List<LogUserEntity> findUserByUserId(String userId) {
        Query q = entityManager.createNamedQuery("findUserViaUserId");

        q.setParameter("userId", userId);
        q.setMaxResults(1);
        try {
            return q.getResultList();
        } catch (Exception e) {
            LOG.error("Not matching user was found.");
            return null;
        }
    }

    public List<AuditLogEntity> findUserLogs(String userId) {
        List<LogUserEntity> user = findUserByUserId(userId);
        if (null != user) {

            Query q = entityManager.createNamedQuery("findLogsViaUser");

            q.setParameter("user", user);
            return q.getResultList();
        }
        return Collections.emptyList();
    }

    public <E extends BaseJpaEntity> E persist(E entity) {
        return entityManager.merge(entity);
    }

    public <E extends BaseJpaEntity> E find(Long id, Class<E> entityClazz) {
        return entityManager.find(entityClazz, id);
    }

    public void clearTable(String tableName) {
        Query q = entityManager.createQuery("DELETE FROM " + tableName);
        int removed = q.executeUpdate();
        LOG.info("Removed {} entries from {}", removed, tableName);
    }
}
