package com.experts.core.biller.statemachine.api.gigaspace.daos.repo;

import com.experts.core.biller.statemachine.api.EmbeddedableClazzz.AuthCoreRoles;
import com.experts.core.biller.statemachine.api.model.domain.jpa.RolesUsers;
import com.experts.core.biller.statemachine.api.model.domain.jpa.UsersCore;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.ws.rs.Consumes;


@Api(tags = "UserRoles Entity")
@RepositoryRestResource(path = "UserRoles" , collectionResourceRel = "UserRoles")
@RestResource(exported = true ,path = "/service/UserRoles" , rel = "/service/UserRoles")
@Consumes("application/json")
public interface UsersRolesRepo extends JpaRepository<RolesUsers,Long> {
    RolesUsers findByAuthCoreRoles(AuthCoreRoles obj);
}
