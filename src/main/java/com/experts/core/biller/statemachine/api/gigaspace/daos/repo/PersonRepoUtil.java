package com.experts.core.biller.statemachine.api.gigaspace.daos.repo;

import com.experts.core.biller.statemachine.api.model.domain.jpa.hr.Person;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.xap.repository.XapRepository;

import javax.ws.rs.Consumes;


@Api(tags = "Person Entity")
@RepositoryRestResource(path = "Person" , collectionResourceRel = "Person")
@RestResource(exported = true ,path = "/service/Person" , rel = "/service/Person")
@Consumes("application/json")
public interface PersonRepoUtil extends JpaRepository<Person,Long> {


    public Person findAllByPhone(String phone);

}
