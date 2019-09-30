package com.experts.core.biller.statemachine.api.model.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menus")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class MenuDO extends AbstractEntity implements Serializable {
    @Transient
    private static final long serialVersionUID = 1351875740601649828L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer menuId;
    private String menuName;
    private Integer menuParentId;
    private String menuRescStr;
    private String menuType;
    private String menuDescription;
    private String menuLevel;
    private String menuStatus;
    @Transient
    private String roleName;
    @Transient
    private List<MenuDO> children = new ArrayList<MenuDO>();

}
