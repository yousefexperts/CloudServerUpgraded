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
import java.util.List;
import java.util.UUID;

@Entity
@Table(name  = "store_info")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Cache(region = "com.experts.core.biller.statemachine.api.model.domain.jpa.hr.engine.Store" , usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Store extends AbstractEntity implements Serializable {


    @Column(name  = "store_name" , nullable = false)
    private String storeName;


    @LastModifiedDate
    private Date creationDate;

    @LastModifiedBy
    private UsersCore usersCore;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "stores")
    private List<About> stores;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "stores")
    private List<AdsOffers> storesOffers;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "stores")
    private List<Branch> branches;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "stores")
    private List<Items> items;


    @Column(name  = "notes" , nullable = true)
    private String notes;

    @Column(name  = "description" , nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name  = "owner_id" , nullable = false)
    private Owner owners;


    @Version
    private int version;

}
