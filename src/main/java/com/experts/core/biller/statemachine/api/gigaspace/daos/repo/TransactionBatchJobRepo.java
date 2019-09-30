package com.experts.core.biller.statemachine.api.gigaspace.daos.repo;

import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.TransactionApproved;
import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.TransactionBatchJob;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.xap.repository.XapRepository;

import javax.ws.rs.Consumes;

@Api(tags = "TransactionBatchJob Entity")
@RepositoryRestResource(path = "TransactionBatchJob" , collectionResourceRel = "TransactionBatchJob")
@RestResource(exported = true ,path = "/service/TransactionBatchJob" , rel = "/service/TransactionBatchJob")
@Consumes("application/json")
public interface TransactionBatchJobRepo extends JpaRepository< TransactionBatchJob ,Long> {
}
