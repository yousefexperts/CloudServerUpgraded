package com.experts.core.biller.statemachine.api.gigaspace.daos.repo;


import com.experts.core.biller.statemachine.api.model.domain.jpa.UsersCore;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.ws.rs.Consumes;


@Api(tags = "UsersCore Entity")
@RepositoryRestResource(path = "UsersCore" , collectionResourceRel = "User")
@RestResource(exported = true ,path = "/service/UsersCore" , rel = "/service/UsersCore")
@Consumes("application/json")
public interface UserDetailsRepo extends JpaRepository<UsersCore, Long> {
    UsersCore findByUserName(String userName);
}
