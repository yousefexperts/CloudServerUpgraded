package co.insight.starter.firebase.repository;

import co.insight.starter.firebase.config.audit.AuditEventConverterFireBase;
import co.insight.starter.firebase.domain.PersistentAuditEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.stereotype.Repository;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.zip.DataFormatException;


@Repository
public class CustomAuditEventRepository extends  InMemoryAuditEventRepository {

    @Autowired
    private PersistenceAuditEventRepository persistenceAuditEventRepository;

    @Autowired
    private AuditEventConverterFireBase auditEventConverterFireBase;


    public List<AuditEvent> find(String principal, LocalDate after , String type) throws DataFormatException {
        Iterable<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findByPrincipalAndAuditEventDateGreaterThan(principal, after);
        return auditEventConverterFireBase.convertToAuditEvent(persistentAuditEvents);
    }
    @Override
    public void add(AuditEvent event) {
        PersistentAuditEvent persistentAuditEvent = new PersistentAuditEvent();
        persistentAuditEvent.setPrincipal(event.getPrincipal());
        persistentAuditEvent.setAuditEventType(event.getType());
        persistentAuditEvent.setAuditEventDate(LocalDate.now());
        persistentAuditEvent.setData(auditEventConverterFireBase.convertDataToStrings(event.getData()));

        persistenceAuditEventRepository.save(persistentAuditEvent);
    }
}
