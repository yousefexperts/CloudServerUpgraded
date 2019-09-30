package com.experts.core.biller.statemachine.api.model.domain.jpa;


import com.experts.core.biller.statemachine.api.constants.Currency;
import com.experts.core.biller.statemachine.api.model.EntityRevisionListener_;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name =  "pay_state")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners({EntityRevisionListener_.class})
@SpaceClass
public class PayState extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name  = "payment_id" , nullable = false)
    private Payment payments;

    @OneToMany( fetch = FetchType.LAZY , mappedBy =  "states" , cascade =  CascadeType.ALL)
    private List<Transaction> transactions;

    
}
