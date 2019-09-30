package com.experts.core.biller.statemachine.api.gigaspace.daos.repo;

import com.experts.core.biller.statemachine.api.model.domain.jpa.settlement.AccountDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.xap.repository.XapRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ws.rs.Consumes;

@Api(tags = "AccountDetails Entity")
@RepositoryRestResource(path = "AccountDetails" , collectionResourceRel = "AccountDetails")
@RestResource(exported = true ,path = "/service/AccountDetails" , rel = "/service/AccountDetails")
@Consumes("application/json")
public interface AccountRepo extends JpaRepository< AccountDetails , Long> {
}
