package co.insight.starter.firebase.creator.firebase;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -1869548136546750302L;
	private final Object token;
	private Object credentials;

	public FirebaseAuthenticationToken(Object principal, Object credentials) {
		super(null);
		this.token = principal;
		this.credentials = credentials;
		setAuthenticated(false);
	}

	public FirebaseAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.token = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);
	}

	public Object getCredentials() {
		return this.credentials;
	}

	public Object getPrincipal() {
		return token;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}

    @Override
    public String toString() {
        return "FirebaseAuthenticationToken{" +
            "principal=" + token +
            ", credentials=" + credentials +
            '}';
    }

    @Override
	public void eraseCredentials() {
		super.eraseCredentials();
		credentials = null;
	}
}
