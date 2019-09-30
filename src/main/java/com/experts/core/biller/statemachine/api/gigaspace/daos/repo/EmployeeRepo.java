package com.experts.core.biller.statemachine.api.gigaspace.daos.repo;

import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.BillerServiceCatogery;
import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.EmployeeDetails;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.xap.repository.XapRepository;

import javax.ws.rs.Consumes;

@Api(tags = "EmployeeDetails Entity")
@RepositoryRestResource(path = "EmployeeDetails" , collectionResourceRel = "EmployeeDetails")
@RestResource(exported = true ,path = "/service/EmployeeDetails" , rel = "/service/EmployeeDetails")
@Consumes("application/json")
public interface EmployeeRepo extends JpaRepository< EmployeeDetails , Long> {
}
