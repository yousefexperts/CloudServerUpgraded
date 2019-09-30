package com.experts.core.biller.statemachine.api.model.domain.jpa.settlement;

import com.experts.core.biller.statemachine.api.model.domain.jpa.AbstractEntity;
import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpaceVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name  = "bank_services")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@SpaceClass
public class BankServices extends AbstractEntity implements Serializable {

    @Column(name  = "name" , nullable = false)
    private String service;

    @Column(name  = "catogrey" , nullable = false)
    private BankServiceCatogeryType catogeryType;

    @Column(name  = "cost" , nullable = false)
    private int cost;

    @Temporal( TemporalType.TIMESTAMP)
    @Column(name  = "start_date" , nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name  = "end_date" , nullable = true)
    private Date endDate;

    @Column(name  = "arabic_name" , nullable = false)
    private String arabicName;

    @Column(name  = "english_name" , nullable = false)
    private String englishName;

    @Column(name  = "arabic_short_name" , nullable = true)
    private String arabicShortName;

    @Column(name =  "english_short_name" , nullable = true)
    private String englishShortName;


}
