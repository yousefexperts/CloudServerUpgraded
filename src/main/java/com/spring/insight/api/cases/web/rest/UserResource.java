package com.spring.insight.api.cases.web.rest;

import co.insight.starter.firebase.creator.Constants;
import co.insight.starter.firebase.domain.Authority;
import co.insight.starter.firebase.domain.User;
import co.insight.starter.firebase.repository.UserRepository;

import com.codahale.metrics.annotation.Timed;
import com.spring.insight.api.cases.security.AuthoritiesConstants;
import com.spring.insight.api.cases.service.UserServiceSelection;
import com.spring.insight.api.cases.web.rest.errors.BadRequestAlertException;
import com.spring.insight.api.cases.web.rest.errors.EmailAlreadyUsedException;
import com.spring.insight.api.cases.web.rest.errors.LoginAlreadyUsedException;
import com.spring.insight.api.cases.web.rest.util.HeaderUtil;
import com.spring.insight.api.cases.web.rest.util.PaginationUtil;
import com.spring.insight.api.cases.web.rest.vm.ManagedUserVM;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "userManagement";

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  UserServiceSelection userServiceSelection;

    @PostMapping("/users")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity createUser(@Valid @RequestBody ManagedUserVM managedUserVM) throws URISyntaxException {
        log.debug("REST request to save User : {}", managedUserVM);

        if (managedUserVM.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", ENTITY_NAME, "idexists");

        } else if (userRepository.findByLogin(managedUserVM.getLogin().toLowerCase()) != null) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findByEmailIgnoreCase(managedUserVM.getEmail()) != null) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userRepository.save(managedUserVM);
          //  mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "A user is created with identifier " + newUser.getLogin(), newUser.getLogin()))
                .body(newUser);
        }
    }

    @PutMapping("/users")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<User> updateUser(@Valid @RequestBody ManagedUserVM managedUserVM) {
        log.debug("REST request to update User : {}", managedUserVM);
        User existingUser = userRepository.findByEmailIgnoreCase(managedUserVM.getEmail());
        if (existingUser != null && (!existingUser.getLogin().equals(managedUserVM.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findByLogin(managedUserVM.getLogin().toLowerCase());
        if (existingUser != null && (!existingUser.getLogin().equals(managedUserVM.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        User users = userRepository.save(managedUserVM);
        Optional<User> opt = Optional.of(users);
        return ResponseUtil.wrapOrNotFound(opt,HeaderUtil.createAlert("A user is updated with identifier " + managedUserVM.getLogin(), managedUserVM.getLogin()));
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@ApiParam Pageable pageable) {
        final Page<User> page = userRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/users/authorities")
    @Secured(AuthoritiesConstants.ADMIN)
    public Set<Authority> getAuthorities() {
        User user = userRepository.findByLogin("admin");
        return user.getAuthorities();
    }

    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<User> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        User users = userRepository.findByLogin(login);
        Optional<User> opt = Optional.of(users);
        return ResponseUtil.wrapOrNotFound(opt);
    }


    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        User user = userRepository.findByLogin(login);
        userRepository.delete(user);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "A user is deleted with identifier " + login, login)).build();
    }
}
