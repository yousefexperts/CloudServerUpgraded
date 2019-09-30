package com.experts.core.biller.statemachine.api.model.domain.jpa;

import com.experts.core.biller.statemachine.api.model.EntityRevisionListener_;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpacePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name  = "completed_state")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners({EntityRevisionListener_.class})
@SpaceClass
public class CompletedState extends AbstractEntity {

    public static final String DB_TABLE = "com/experts/core/biller/statemachine/api/vertxloader";

    @Column(name  = "name" , nullable = false)
    private String name;

    @Column(name  = "code" , nullable = false)
    private String code;

    @Column(name  = "message" , nullable = false)
    private String message;

    @Column(name  = "execption" , nullable = true)
    private String execption;

    @Column(name  = "sucess" , nullable = false)
    private boolean isSuccess;

    @OneToOne
    @JoinColumn(name  = "processstate_completedState" , nullable = false)
    private ProcessState processstate_completedState;

    @Column(name  = "bill_no" , nullable = false)
    private String billNo;

    @Column(name  = "bill_code" , nullable = false)
    private String billCode;

    @Column(name  = "transaction_id" , nullable = false)
    private String transactionId;

    @Column(name  = "biller_name" , nullable = false)
    private String billerName;

    @Column(name  = "biller_code" , nullable = false)
    private String billerCode;


}
