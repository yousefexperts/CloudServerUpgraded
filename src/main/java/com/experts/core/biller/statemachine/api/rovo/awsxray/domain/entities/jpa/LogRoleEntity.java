package com.experts.core.biller.statemachine.api.rovo.awsxray.domain.entities.jpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="role")
public class LogRoleEntity extends BaseJpaEntity {

    @Column(name = "name", unique = true, nullable = false, length = 25)
    private String roleName;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private List<LogUserEntity> users = new ArrayList<>(0);

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<LogUserEntity> getUsers() {
        return this.users;
    }

    public void setUsers(List<LogUserEntity> user) {
        this.users = user;
    }
}
