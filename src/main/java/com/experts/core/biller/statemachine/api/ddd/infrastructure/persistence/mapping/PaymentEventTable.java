package com.experts.core.biller.statemachine.api.ddd.infrastructure.persistence.mapping;

import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentEventType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"eventData", "timestamp"})
@ToString
@Entity
@Table(name  = "PaymentEventTable")
public class PaymentEventTable {
    
    @Id
    /*@GeneratedValue(strategy =  GenerationType.IDENTITY)*/
    private String id;

    private PaymentEventType eventType;
    private String paymentId;
    private LocalDateTime timestamp;
    @Column(length = 1024)
    private String eventData;
}
