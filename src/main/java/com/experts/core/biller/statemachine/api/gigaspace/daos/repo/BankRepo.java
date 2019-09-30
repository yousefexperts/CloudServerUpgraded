package com.experts.core.biller.statemachine.api.gigaspace.daos.repo;

import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.Bank;
import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.TransactionApproved;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.xap.repository.XapRepository;

import javax.ws.rs.Consumes;

@Api(tags = "Bank Entity")
@RepositoryRestResource(path = "Bank" , collectionResourceRel = "Bank")
@RestResource(exported = true ,path = "/service/Bank" , rel = "/service/Bank")
@Consumes("application/json")
public interface BankRepo extends JpaRepository< Bank , Long> {
}
