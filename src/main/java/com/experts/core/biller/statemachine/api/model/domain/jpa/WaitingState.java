package com.experts.core.biller.statemachine.api.model.domain.jpa;

import com.experts.core.biller.statemachine.api.model.EntityRevisionListener_;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name  = "waiting_state")
@Data
@EntityListeners({EntityRevisionListener_.class})
public class WaitingState extends AbstractEntity {

    public static final String DB_TABLE = "com/experts/core/biller/statemachine/api/vertxloader";

    @Column(name  = "waiting_ops_name" , nullable = false)
    private String waitingOpsName;

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name =  "process_id" , nullable = false)
    private ProcessState processState;

    @Column(name  = "period" , nullable = false)
    private String period;

    @Column(name  = "bill_code" , nullable = false)
    private String billCode;

    @Column(name  = "bill_name" , nullable = false)
    private String billName;

    @Column(name  = "bank_code" , nullable = false)
    private String bankCode;

    @Column(name  = "biller_code" , nullable = false)
    private String billerCode;

    @Column(name  = "transaction_id" , nullable = false)
    private String transactionId;

    @JsonCreator(mode  = JsonCreator.Mode.PROPERTIES)
    public WaitingState(){}




    public WaitingState(String waitingOpsName , String period , String billCode , String billName , String bankCode , String billerCode , String transactionId){
        this.waitingOpsName = waitingOpsName;
        this.period = period;
        this.bankCode = bankCode;
        this.billCode =  billCode;
        this.billName = billName;
        this.billerCode =  billerCode;
        this.transactionId = transactionId;
    }


}
