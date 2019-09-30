package com.experts.core.biller.statemachine.api.rovo.awsxray.security;

import com.experts.core.biller.statemachine.api.rovo.awsxray.domain.UserServiceQyef;
import com.experts.core.biller.statemachine.api.rovo.awsxray.domain.views.AuthenticationUserViewEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class AppUserDetailsService implements UserDetailsService {

    @Resource
    private UserServiceQyef userServiceQyef;

    protected AppUser createAppUser(AuthenticationUserViewEntity user) {

        List<String> roles = user.getRoles();

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (null != roles && roles.size() > 0) {
            for (String role : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_UNAUTHENTICATED"));
        }

        return new AppUser(user.getUserId(), user.getPasswordHash(), user.getUserKey(), grantedAuthorities);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        AuthenticationUserViewEntity user = userServiceQyef.findUserView(userId);

        if (user == null) {
            throw new UsernameNotFoundException("No matching user with given credentials found");
        }

        return createAppUser(user);
    }
}
