package co.insight.starter.firebase.repository;

import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.insight.starter.firebase.domain.PersistentAuditEvent;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


 public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEvent, String> {

    List<PersistentAuditEvent> findByPrincipal(String principal);

    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateGreaterThan(String principal, LocalDate after);
    
    @Query("select p from PersistentAuditEvent p where p.auditEventDate >= ?1 and p.auditEventDate <= ?2")
    List<PersistentAuditEvent> findByDates(LocalDate fromDate, LocalDate toDate);

     List<PersistentAuditEvent> findByAuditEventDateAfter(Date after);
     
     List<PersistentAuditEvent> findById(String id , Pageable pageable);
}
