package com.experts.core.biller.statemachine.api.model.domain.jpa;

import com.experts.core.biller.statemachine.api.EmbeddedableClazzz.AuthCoreRoles;
import com.experts.core.biller.statemachine.api.gigaspace.daos.repo.UsersRolesRepo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.jooq.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name  = "user_roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL )
public class RolesUsers implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name="uuid", strategy="uuid2" )
    private UUID id;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="userId", column = @Column(name="user_id" , length = 255) ),
            @AttributeOverride(name="roles", column = @Column(name="role_id" , length = 255) )
    } )
    private AuthCoreRoles authCoreRoles;

    @Version
    private int version;

    public static RolesUsers create(String username , String password){
        UsersCore coreObject = UsersCore.create(username , password);
        AuthCoreRoles roles = new AuthCoreRoles();
        roles.setUserId(coreObject);
        Roles auth = Roles.create();
        roles.setRoles(auth);
        RolesUsers rolesUsers = new RolesUsers();
        rolesUsers.setAuthCoreRoles(roles);
        UUID uuid = UUID.randomUUID();
        rolesUsers.setId(uuid);
        return rolesUsers;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AuthCoreRoles getAuthCoreRoles() {
        return authCoreRoles;
    }

    public void setAuthCoreRoles(AuthCoreRoles authCoreRoles) {
        this.authCoreRoles = authCoreRoles;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
