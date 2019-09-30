package com.experts.core.biller.statemachine.api.model.domain.jpa;

import com.experts.core.biller.statemachine.api.order.TransactionStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NegativeOrZero;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name  = "transaction_status")
public class TransactionStatus extends AbstractEntity {



    private TransactionStatusType status;


    public TransactionStatusType getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusType status) {
        this.status = status;
    }
}
