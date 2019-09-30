package co.insight.starter.firebase.domain;

import com.experts.core.biller.statemachine.api.model.domain.jpa.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Entity
@Table(name = "AUDIT_EVENT")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersistentAuditEvent extends AbstractEntity {



    @NotNull
    @Column(nullable = false)
    private String principal;

    @Column(name = "event_date")
    private LocalDate auditEventDate;
    
    @Column(name = "event_type")
    private String auditEventType;

    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @Lob
    @CollectionTable(name="AUDIT_EVENT_DATA",  joinColumns=@JoinColumn(name="id") )
    private Map<String, String> data = new HashMap<>();


}
