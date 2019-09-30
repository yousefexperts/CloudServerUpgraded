package com.experts.core.biller.statemachine.api.EmbeddedableClazzz;

import com.experts.core.biller.statemachine.api.model.domain.jpa.Roles;
import com.experts.core.biller.statemachine.api.model.domain.jpa.UsersCore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.validation.constraints.NegativeOrZero;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthCoreRoles implements Serializable {

    private UsersCore userId;


    private Roles roles;

}
