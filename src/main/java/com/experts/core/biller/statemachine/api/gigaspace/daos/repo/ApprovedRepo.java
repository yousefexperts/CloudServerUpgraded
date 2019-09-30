package com.experts.core.biller.statemachine.api.gigaspace.daos.repo;

import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.TransactionApproved;
import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.TransactionQueue;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.xap.repository.XapRepository;

import javax.ws.rs.Consumes;

@Api(tags = "TransactionApproved Entity")
@RepositoryRestResource(path = "TransactionApproved" , collectionResourceRel = "TransactionApproved")
@RestResource(exported = true ,path = "/service/TransactionApproved" , rel = "/service/TransactionApproved")
@Consumes("application/json")
public interface ApprovedRepo extends JpaRepository< TransactionApproved , Long> {
}
