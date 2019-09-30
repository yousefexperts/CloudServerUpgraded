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
@Table(name = "role_menu")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Data
public class RoleMenuDOAccess extends AbstractEntity implements Serializable{
    @Transient
    private static final long serialVersionUID = 1571505167808462841L;
    private Integer roleId;
    private Integer menuId;
}
