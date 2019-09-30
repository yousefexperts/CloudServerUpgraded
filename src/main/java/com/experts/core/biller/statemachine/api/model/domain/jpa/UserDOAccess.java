package com.experts.core.biller.statemachine.api.model.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name = "user_core_api")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@org.hibernate.annotations.Cache(usage =  CacheConcurrencyStrategy.TRANSACTIONAL)
public class UserDOAccess extends AbstractEntity implements Serializable {
    @Transient
    private static final long serialVersionUID = -819110431211429480L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String userName;
    private String nickname;
    private String password;
    private String idType;
    private String idNo;
    private String email;
    private String phone;
    private String userStatus;
    private Timestamp createTime;
    private Timestamp invalidTime;
    @Transient
    private List<RoleDOAccess> roleList;
}
