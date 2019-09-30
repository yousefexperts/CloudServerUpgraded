package com.experts.core.biller.statemachine.api.gigaspace.daos.repo;

import com.experts.core.biller.statemachine.api.model.domain.jpa.UsersCore;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.ws.rs.Consumes;

@Api(tags = "UserCore Entity")
@RepositoryRestResource(path = "UserCore" , collectionResourceRel = "UserCore")
@RestResource(exported = true ,path = "/service/UserCore" , rel = "/service/UserCore")
@Consumes("application/json")
public interface UserCoreRepo extends JpaRepository<UsersCore,Long> {

    public UsersCore findByUserName(String username);
}
