package com.experts.core.biller.statemachine.api.model.domain.jpa.settlement;


import com.experts.core.biller.statemachine.api.model.domain.jpa.AbstractEntity;
import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpacePersist;
import com.gigaspaces.annotation.pojo.SpaceVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name  = "transaction_batch_job")
@SpaceClass
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactionBatchJob extends AbstractEntity implements Serializable {

    @Column(name  = "name" , nullable = false)
    private String name;

    @Column(name  = "description" , nullable = true)
    private String description;

    @Column(name   = "success" , nullable = false)
    private boolean isSuccess;

    @Column(name  = "request" , nullable = false)
    private String request;

    @Column(name = "response" , nullable = false)
    private String response;

    @Column(name  = "exception" , nullable = true)
    private String exception;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name  = "transaction_id" , nullable = false)
    private TransactionCreation transactionCreation;

}
