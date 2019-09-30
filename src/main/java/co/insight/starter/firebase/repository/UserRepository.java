package co.insight.starter.firebase.repository;


import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.insight.api.cases.web.rest.vm.ManagedUserVM;

import co.insight.starter.firebase.domain.User;

import java.util.List;

import javax.validation.Valid;


public interface UserRepository extends JpaRepository<User, String> {
    
    @Query("select u from User u where u.activationKey = ?1")
    User getUserByActivationKey(String activationKey);
    
    @Query("select u from User u where u.activated = false and u.createdDate > ?1")
    List<User> findNotActivatedUsersByCreationDateBefore(DateTime dateTime);

    User findByLogin(String login);
    
    User findByEmailIgnoreCase(String email);
    
    User save(@Valid ManagedUserVM managedUserVM);


    
    
} 
