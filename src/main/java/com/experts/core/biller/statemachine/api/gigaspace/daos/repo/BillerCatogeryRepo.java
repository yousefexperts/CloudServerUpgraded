package com.experts.core.biller.statemachine.api.gigaspace.daos.repo;

import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.BillerServiceCatogery;
import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.TransactionBatchJob;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.xap.repository.XapRepository;

import javax.ws.rs.Consumes;

@Api(tags = "BillerServiceCatogery Entity")
@RepositoryRestResource(path = "BillerServiceCatogery" , collectionResourceRel = "BillerServiceCatogery")
@RestResource(exported = true ,path = "/service/BillerServiceCatogery" , rel = "/service/BillerServiceCatogery")
@Consumes("application/json")
public interface BillerCatogeryRepo extends JpaRepository< BillerServiceCatogery , Long> {
}
