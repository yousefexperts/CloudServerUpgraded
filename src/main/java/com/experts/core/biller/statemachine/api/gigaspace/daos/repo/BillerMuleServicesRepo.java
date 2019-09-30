package com.experts.core.biller.statemachine.api.gigaspace.daos.repo;

import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.BillerServiceCatogery;
import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.BillersMuleServices;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.xap.repository.XapRepository;

import javax.ws.rs.Consumes;


@Api(tags = "BillersMuleServices Entity")
@RepositoryRestResource(path = "BillersMuleServices" , collectionResourceRel = "BillersMuleServices")
@RestResource(exported = true ,path = "/service/BillersMuleServices" , rel = "/service/BillersMuleServices")
@Consumes("application/json")
public interface BillerMuleServicesRepo extends JpaRepository< BillersMuleServices , Long> {
}
