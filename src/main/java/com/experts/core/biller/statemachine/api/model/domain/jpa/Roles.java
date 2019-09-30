package com.experts.core.biller.statemachine.api.model.domain.jpa;

import com.experts.core.biller.statemachine.api.model.EntityRevisionListener_;
import com.gigaspaces.annotation.pojo.SpaceClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;

import javax.management.relation.Role;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners({EntityRevisionListener_.class})
@SpaceClass
@Table(name  = "roles_auth")
public class Roles implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name="uuid", strategy="uuid2" )
    private UUID id;

    @Column(name  = "auth" , nullable = false )
    private String authName;


    public static Roles create(){

        UUID id = UUID.randomUUID();
        Roles roles = Roles.builder().authName("ROLE_ADMIN").id(id).build();
        return  roles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }
}
