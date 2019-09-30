package com.experts.core.biller.statemachine.api.model.domain.jpa;

import javax.persistence.*;

import com.experts.core.biller.statemachine.api.model.EntityRevisionListener_;
import com.gigaspaces.annotation.pojo.SpaceClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Payments")
@EntityListeners({EntityRevisionListener_.class})
@SpaceClass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Payment extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id" , nullable = false)
    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
