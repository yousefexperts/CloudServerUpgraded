package com.experts.core.biller.statemachine.api.service.impl;


import com.experts.core.biller.statemachine.api.auth.WebSecurityConfigApp;
import com.experts.core.biller.statemachine.api.gigaspace.daos.repo.UserCoreRepo;
import com.experts.core.biller.statemachine.api.model.domain.jpa.UsersCore;
import com.experts.core.biller.statemachine.api.model.domain.jpa.hr.Person;
import com.experts.core.biller.statemachine.api.gigaspace.daos.repo.PersonRepoUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Component
@Import(WebSecurityConfigApp.class)
public class QyefIndexingController {

    private String amount;
    private String phone;
    private String fullName;
    private String password;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PersonRepoUtil personRepoUtil;

    @Autowired
    private UserCoreRepo userCoreRepo;

    @PostMapping(value = "/api/create/qyef/person")
    @ApiOperation(value = "/api/create/qyef/person", code = 200)
    @ApiResponse(response = QyefSuccessResponse.class, code = 200, message = "sucess")
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public ResponseEntity<QyefSuccessResponse> index(@RequestBody QuefRequestBody[] request) {
        if (request != null && request.length != 0) {
            amount = request[0].getAmount();
            phone = request[0].getPhone();
            fullName = request[0].getFullName();
            Person exist = personRepoUtil.findAllByPhone(phone);
            if (exist == null) {
                Person person = Person.create(request[0]);
                personRepoUtil.save(person);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(QyefSuccessResponse.create());
            }
            return ResponseEntity.status(HttpStatus.OK).body(QyefSuccessResponse.create());
        } else
            return ResponseEntity.status(HttpStatus.OK).body(QyefSuccessResponse.create());
    }


    @PostMapping(value = "/api/create/qyef/create")
    @ApiOperation(value = "/api/create/qyef/create", code = 200)
    @ApiResponse(response = QyefSuccessResponse.class, code = 200, message = "sucess")
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public ResponseEntity<QyefSuccessResponse> sigin(@RequestBody QyefCreateUser[] request) {
        if (request != null && request.length != 0) {
            phone = request[0].getPhone();
            UsersCore exist = userCoreRepo.findByUserName(phone);
            if (exist == null) {
                UsersCore person = UsersCore.create(request[0].getPhone(), request[0].getFullName());
                userCoreRepo.save(person);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(QyefSuccessResponse.create());
            }
            return ResponseEntity.status(HttpStatus.OK).body(QyefSuccessResponse.create());
        } else
            return ResponseEntity.status(HttpStatus.OK).body(QyefSuccessResponse.create());
    }


    @PostMapping(value = "/selectUserName", path = "/selectUserName", name = "/selectUserName")
    @ApiOperation(value = "/selectUserName", code = 200)
    @ApiResponse(response = QyefSuccessResponse.class, code = 200, message = "sucess")
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public ResponseEntity<QyefSuccessResponse> selectUserName(@RequestBody SuccessLogin[] login) {
        SuccessLogin starter = login[0];
        try {
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(starter.getJ_username(), starter.getJ_password());
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
        }catch(Exception io){io.printStackTrace();}
        return ResponseEntity.status(HttpStatus.OK).body(QyefSuccessResponse.create());
    }

}
