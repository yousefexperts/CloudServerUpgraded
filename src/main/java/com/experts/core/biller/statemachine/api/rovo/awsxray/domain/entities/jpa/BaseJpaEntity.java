package com.experts.core.biller.statemachine.api.rovo.awsxray.domain.entities.jpa;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseJpaEntity implements JpaEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date creationDate;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastChange;

    boolean disabled;

    @PrePersist
    @PreUpdate
    public void prePersistAndUpdate() {
        creationDate = creationDate == null ? new Date() : creationDate;
        lastChange = lastChange == null ? creationDate : new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getStringId() {
        return getId() == null ? null : getId().toString();
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setLastChange(Date lastChange){
        this.lastChange = lastChange;
    }
}
