package com.experts.core.biller.statemachine.api.model.domain.jpa;


import com.experts.core.biller.statemachine.api.model.EntityRevisionListener_;
import com.experts.core.biller.statemachine.api.model.domain.jpa.hr.Person;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.gigaspaces.annotation.pojo.SpaceClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users_sys")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners({EntityRevisionListener_.class})
@SpaceClass
public class UsersCore extends AbstractEntity implements Serializable {

    @Version
    private int version;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name  = "amount" , nullable = false)
    private String amount;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "blocked", nullable = true)
    private boolean isBlocked;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name  = "person_id" , nullable = false)
    private Person persons;


    public static UsersCore create(String userName , String password){

        UsersCore core = UsersCore.builder().isBlocked(false).enabled(true).password(password).userName(userName).build();
        return core;
    }

}
