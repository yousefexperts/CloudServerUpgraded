package co.insight.starter.firebase.creator.firebase;

import co.insight.starter.firebase.domain.User;
import co.insight.starter.firebase.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.spring.insight.api.cases.service.exception.FirebaseUserNotExistsException;


@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

    public static final String NAME="FirebaseAuthenticationProvider";

    @Autowired
	private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    private String userName;

    @Autowired
    private  AuthenticationManager authenticationManager;

	public boolean supports(Class<?> authentication) {
		return (FirebaseAuthenticationToken.class.isAssignableFrom(authentication));
	}

    public Authentication authenticate(Authentication authentication) throws AuthenticationException , FirebaseUserNotExistsException  {
        if (!supports(authentication.getClass())) {
            return null;
        }

        FirebaseAuthenticationToken authenticationToken = (FirebaseAuthenticationToken) authentication;
        User details = userRepository.findByLogin(authenticationToken.getName());
        if (details == null) {
            throw new FirebaseUserNotExistsException();
        }
        UserDetails details1Core = userDetailsService.loadUserByUsername(details.getLogin());
        authenticationToken = new FirebaseAuthenticationToken(details1Core, authentication.getCredentials(), details1Core.getAuthorities());
        return authenticationToken;
    }


}
