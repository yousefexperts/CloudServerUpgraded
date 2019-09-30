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
@Table(name  = "store_item_catogery")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL ,  region = "com.experts.core.biller.statemachine.api.model.domain.jpa.hr.engine.Catogery")
public class Catogery extends AbstractEntity implements Serializable {


    @LastModifiedDate
    private Date creationDate;

    @LastModifiedBy
    private UsersCore usersCore;


    @Column(name = "cat_name" , nullable = false)
    private String catName;

    @Column(name = "is_enabled" , nullable = false)
    private boolean enabled=true;



    @ManyToOne(fetch = FetchType.LAZY , cascade =  CascadeType.ALL)
    @JoinColumn(name  = "item_id" , nullable = false)
    private Items items;



    @Column(name  = "notes" , nullable = true)
    private String notes;

    @Column(name  = "description" , nullable = true)
    private String description;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "parents")
    private List<SubCatogery> subCatogeries;


    @Version
    private int version;


}
