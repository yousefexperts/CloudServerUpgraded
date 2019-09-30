package com.experts.core.biller.statemachine.api.model.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@Table(name = "role_second")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class RoleUserDOAccess extends AbstractEntity implements Serializable{
    @Transient
    private static final long serialVersionUID = 3550776327278345622L;
    private Integer userId;
    private Integer roleId;
}
