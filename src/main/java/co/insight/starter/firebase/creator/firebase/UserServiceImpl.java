package co.insight.starter.firebase.creator.firebase;

import co.insight.starter.firebase.domain.Authority;
import co.insight.starter.firebase.domain.User;
import co.insight.starter.firebase.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class UserServiceImpl implements UserDetailsService {

@Autowired
private UserRepository userRepository;

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println(username);
    User user =  userRepository.findByLogin(username);
    Set<GrantedAuthority> authorities = buildUserAuthority(user.getAuthorities());
    System.out.println("after....");
    return buildUserForAuthentication(user, authorities);
}

private Set<GrantedAuthority> buildUserAuthority(Set<Authority> userRoles) {
    Set<GrantedAuthority> setAuths = new HashSet<>();
    for(Authority userRole  : userRoles){
        System.out.println("called buildUserAuthority(Set<UserRole> userRoles) method.....");
        setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
    }

    Set<GrantedAuthority> grantedAuthorities = new HashSet<>(setAuths);
    return grantedAuthorities;
}

private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, Set<GrantedAuthority> authorities) {

    System.out.println("called buildUserForAuthentication(Users user, List<GrantedAuthority> authorities) method....");
    return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), user.isActivated(), true, true, true, authorities);
}}
