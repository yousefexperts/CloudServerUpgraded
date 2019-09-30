package com.experts.core.biller.statemachine.api.gigaspace.daos.repo;

import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.EmployeeDetails;
import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.TransactionQueue;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.xap.repository.XapRepository;

import javax.ws.rs.Consumes;

@Api(tags = "TransactionQueue Entity")
@RepositoryRestResource(path = "TransactionQueue" , collectionResourceRel = "TransactionQueue")
@RestResource(exported = true ,path = "/service/TransactionQueue" , rel = "/service/TransactionQueue")
@Consumes("application/json")
public interface QueueRepo extends JpaRepository< TransactionQueue , Long> {
}
