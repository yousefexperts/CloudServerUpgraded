package com.spring.insight.api.cases.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Entity
@Table(name = "persist_test")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersistentAuditEventCore implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String principal;

    @Column(name = "event_date")
    private LocalDate auditEventDate;


    @Column(name = "event_type")
    private String auditEventType;

    @ElementCollection
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    @Lob
    @CollectionTable(name = "persistent_audit", joinColumns=@JoinColumn(name="id"))
    private Map<String, String> data = new HashMap<>();


}
