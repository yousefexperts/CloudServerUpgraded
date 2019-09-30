package co.insight.starter.firebase.service;


import co.insight.starter.firebase.config.audit.AuditEventConverterFireBase;
import co.insight.starter.firebase.domain.PersistentAuditEvent;
import co.insight.starter.firebase.repository.PersistenceAuditEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;


@Service("auditEventServiceFireBase")
@Component("auditEventServiceFireBase")
@Transactional
public class AuditEventServiceFireBase {

    @Autowired
    private PersistenceAuditEventRepository persistenceAuditEventRepository;

    @Autowired
    private AuditEventConverterFireBase auditEventConverter;

    public Page<AuditEvent> findAll(Pageable pageable) {
    	List<PersistentAuditEvent> evts = persistenceAuditEventRepository.findAll(); 
    	List<AuditEvent> events = auditEventConverter.convertToAuditEvent(evts);
    	Page<AuditEvent> pages = new PageImpl<AuditEvent>(events, pageable, events.size());
        return pages;
    }

    public Page<AuditEvent> findByDates(LocalDate fromDate, LocalDate toDate , Pageable pageable) {
        final List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findByDates(fromDate, toDate);
        List<AuditEvent> events = auditEventConverter.convertToAuditEvent(persistentAuditEvents);
    	Page<AuditEvent> pages = new PageImpl<AuditEvent>(events, pageable, events.size());
        return pages;
    }
    
    public Page<AuditEvent> findById(String id , Pageable pageable) {
    	final List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findById(id , pageable);
    	Iterable<PersistentAuditEvent> iterable = persistentAuditEvents;
    	List<AuditEvent> events = auditEventConverter.convertToAuditEvent(iterable);
    	
    	Page<AuditEvent> pages = new PageImpl<AuditEvent>(events, pageable, events.size());
            return pages;
    }
}
