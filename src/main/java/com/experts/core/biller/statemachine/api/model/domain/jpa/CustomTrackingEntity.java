package com.experts.core.biller.statemachine.api.model.domain.jpa;

import com.experts.core.biller.statemachine.api.model.EntityRevisionListener_;
import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceVersion;
import lombok.Data;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@RevisionEntity(EntityRevisionListener_.class)
@Data
@SpaceClass
public class CustomTrackingEntity extends DefaultRevisionEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long clazz;

    @Column(name  = "username" , nullable = false)
    private String username;

    private int version;

    @Column(name = "entity_name" , nullable = false)
    private String entityName;


    @SpaceVersion
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }



    @OneToMany(mappedBy="customTrackingEntity", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CustomRevisionEntity> modifiedEntityTypes = new ArrayList<>();







}
