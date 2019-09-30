package co.insight.starter.firebase.web.rest;

import co.insight.starter.firebase.domain.User;
import co.insight.starter.firebase.repository.UserRepository;
import co.insight.starter.firebase.security.AuthoritiesConstants;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/app")
public class UserResourceFirebase {
    private final Logger log = LoggerFactory.getLogger(UserResourceFirebase.class);

    @Inject
    private UserRepository userRepository;

    @RequestMapping(value = "/rest/users/{login}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public User getUser(@PathVariable String login, HttpServletResponse response) {
        log.debug("REST request to get User : {}", login);
        User user = userRepository.getOne(login);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return user;
    }

}
