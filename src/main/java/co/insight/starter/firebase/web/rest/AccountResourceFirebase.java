package co.insight.starter.firebase.web.rest;

import co.insight.starter.firebase.domain.Authority;
import co.insight.starter.firebase.domain.PersistentToken;
import co.insight.starter.firebase.domain.User;
import co.insight.starter.firebase.repository.PersistentTokenRepository;
import co.insight.starter.firebase.repository.UserRepository;
import co.insight.starter.firebase.security.SecurityUtils;
import co.insight.starter.firebase.service.MailService;
import co.insight.starter.firebase.service.UserQyef;
import co.insight.starter.firebase.web.rest.dto.UserDTO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping("/app")
@Import(UserQyef.class)
public class AccountResourceFirebase {

    private final Logger log = LoggerFactory.getLogger(AccountResourceFirebase.class);

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserQyef userQyef;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/rest/register", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerAccount(@RequestBody UserDTO userDTO, HttpServletRequest request, HttpServletResponse response){
        User user = userRepository.getOne(userDTO.getLogin());
        if (user != null) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } else {
            user = userQyef.createUserInformation(userDTO.getLogin(), userDTO.getPassword(), userDTO.getFirstName(),userDTO.getLastName(), userDTO.getEmail().toLowerCase(), userDTO.getLangKey());
            final Locale locale = Locale.forLanguageTag(user.getLangKey());

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/rest/activate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        User user = userQyef.activateRegistration(key);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(user.getLogin(), HttpStatus.OK);
    }


    @RequestMapping(value = "/rest/authenticate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }


    @RequestMapping(value = "/rest/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getAccount() {
        User user = userQyef.getUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<String> roles = new ArrayList<>();
        for (Authority authority : user.getAuthorities()) {
            roles.add(authority.getName());
        }
        return new ResponseEntity<>(
            new UserDTO(
                user.getLogin(),
                null,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getLangKey(),
                roles),
            HttpStatus.OK);
    }


    @RequestMapping(value = "/rest/account", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void saveAccount(@RequestBody UserDTO userDTO) {
        userQyef.updateUserInformation(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail());
    }


    @RequestMapping(value = "/rest/account/change_password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody String password) {
        if (StringUtils.isEmpty(password)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        userQyef.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/rest/account/sessions", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersistentToken>> getCurrentSessions() {
        User user = userRepository.getOne(SecurityUtils.getCurrentLogin());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(
            persistentTokenRepository.findByUser(user),
            HttpStatus.OK);
    }


    @RequestMapping(value = "/rest/account/sessions/{series}",method = RequestMethod.DELETE)
    public void invalidateSession(@PathVariable String series) throws UnsupportedEncodingException {
        String decodedSeries = URLDecoder.decode(series, "UTF-8");
        User user = userRepository.getOne(SecurityUtils.getCurrentLogin());
        List<PersistentToken> persistentTokens = persistentTokenRepository.findByUser(user);
        for (PersistentToken persistentToken : persistentTokens) {
            if (StringUtils.equals(persistentToken.getId().toString(), decodedSeries)) {
                persistentTokenRepository.delete(persistentToken);
            }
        }
    }

}
