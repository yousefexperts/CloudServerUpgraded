package com.experts.core.biller.statemachine.api.model.domain.jpa.hr;


import com.experts.core.biller.statemachine.api.model.domain.jpa.UsersCore;
import com.experts.core.biller.statemachine.api.model.domain.jpa.hr.engine.Owner;
import com.experts.core.biller.statemachine.api.service.impl.QuefRequestBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jini.id.Uuid;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
/*import org.hibernate.search.annotations.Indexed;*/

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Person extends EntityHrMapper {

    @Column(name  = "full_name" , nullable = false)
    private String fullName;

    @Column(name  = "email" , nullable = false)
    private String email;

    @Column(name  = "phone" , nullable = false)
    private String phone;

    @Column(name  = "active_code" , nullable = false)
    private String activeCode;

    @Column(name = "last_ask_code"  , nullable = false)
    private String lastAskedCodeActive;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "persons")
    private List<UsersCore> usersCores;

    @Version
    private Long version;

    @OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL , mappedBy = "persons")
    private List<Owner> owners;

    public static  Person create(QuefRequestBody body) {
        UUID uuid = UUID.randomUUID();
        Person person =  Person.builder().email(body.getEmail()).fullName(body.getFullName()).phone(body.getPhone()).activeCode(uuid.toString()).build();
        return person;

    }

}
