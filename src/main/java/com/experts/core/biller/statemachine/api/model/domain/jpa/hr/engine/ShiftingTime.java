package com.experts.core.biller.statemachine.api.model.domain.jpa.hr.engine;

import com.experts.core.biller.statemachine.api.model.domain.jpa.AbstractEntity;
import com.experts.core.biller.statemachine.api.model.domain.jpa.UsersCore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name  = "store_shifting_time")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL , region = "com.experts.core.biller.statemachine.api.model.domain.jpa.hr.engine.ShiftingTime")
public class ShiftingTime extends AbstractEntity implements Serializable {



    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name  = "store_id" , nullable = false)
    private Store stores;

    @LastModifiedDate
    private Date creationDate;

    @LastModifiedBy
    private UsersCore usersCore;

    @Column(name  = "notes" , nullable = true)
    private String notes;

    @Column(name  = "description" , nullable = true)
    private String description;


    @Column(name  = "start_date" , nullable = false)
    @Temporal(TemporalType.TIME)
    private Date startDate;

    @Column(name  = "end_date" , nullable = false)
    @Temporal(TemporalType.TIME)
    private Date endDate;

    @Column(name  = "stand_date" , nullable = false)
    @Temporal(TemporalType.DATE)
    private Date standDate;



    @Version
    private int version;



}
