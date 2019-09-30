package com.experts.core.biller.statemachine.api.cas.authentication;

import org.jasig.cas.Message;
import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.jasig.cas.authentication.support.LdapPasswordPolicyConfiguration;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.auth.AuthenticationRequest;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.AuthenticationResultCode;
import org.ldaptive.auth.Authenticator;

import com.experts.core.biller.statemachine.api.cas.jdbc.StorageLogin;

import javax.annotation.PostConstruct;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.validation.constraints.NotNull;
import java.security.GeneralSecurityException;
import java.util.*;


public class LdapAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {


    @NotNull
    private final Authenticator authenticator;

    private StorageLogin storageLogin;

    public void setStorageLogin(StorageLogin storageLogin) {
        this.storageLogin = storageLogin;
    }


    @NotNull
    private String name = LdapAuthenticationHandler.class.getSimpleName();


    private String principalIdAttribute;


    private boolean allowMultiplePrincipalAttributeValues = false;


    @NotNull
    protected Map<String, String> principalAttributeMap = Collections.emptyMap();


    @NotNull
    protected List<String> additionalAttributes = Collections.emptyList();

    private String[] authenticatedEntryAttributes;


    public LdapAuthenticationHandler(@NotNull final Authenticator authenticator) {
        this.authenticator = authenticator;
    }


    public void setName(final String name) {
        this.name = name;
    }


    public void setPrincipalIdAttribute(final String attributeName) {
        this.principalIdAttribute = attributeName;
    }


    public void setAllowMultiplePrincipalAttributeValues(final boolean allowed) {
        this.allowMultiplePrincipalAttributeValues = allowed;
    }


    public void setPrincipalAttributeMap(final Map<String, String> attributeNameMap) {
        this.principalAttributeMap = attributeNameMap;
    }


    public void setAdditionalAttributes(final List<String> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    @Override
    protected HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential upc)
            throws GeneralSecurityException, PreventedException {
        final AuthenticationResponse response;
        try {
            logger.debug("Attempting LDAP authentication for {}", upc);
            final AuthenticationRequest request = new AuthenticationRequest(upc.getUsername(),
                    new org.ldaptive.Credential(upc.getPassword()),
                    this.authenticatedEntryAttributes);
            response = this.authenticator.authenticate(request);
        } catch (final LdapException e) {
            throw new PreventedException("Unexpected LDAP error", e);
        }
        logger.debug("LDAP response: {}", response);

        final List<Message> messageList;

        final LdapPasswordPolicyConfiguration ldapPasswordPolicyConfiguration =
                (LdapPasswordPolicyConfiguration) super.getPasswordPolicyConfiguration();
        if (ldapPasswordPolicyConfiguration != null) {
            logger.debug("Applying password policy to {}", response);
            messageList = ldapPasswordPolicyConfiguration.getAccountStateHandler().handle(
                    response, ldapPasswordPolicyConfiguration);
        } else {
            messageList = Collections.emptyList();
        }

        if (response.getResult()) {
            return createHandlerResult(upc, createPrincipal(upc.getUsername(), response.getLdapEntry()), messageList);
        }

        if (AuthenticationResultCode.DN_RESOLUTION_FAILURE == response.getAuthenticationResultCode()) {
            throw new AccountNotFoundException(upc.getUsername() + " not found.");
        }
        throw new FailedLoginException("Invalid credentials.");
    }

    @Override
    public boolean supports(final Credential credential) {
        return credential instanceof UsernamePasswordCredential;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Creates a CAS principal with attributes if the LDAP entry contains principal attributes.
     *
     * @param username  Username that was successfully authenticated which is used for principal ID when
     *                  {@link #setPrincipalIdAttribute(String)} is not specified.
     * @param ldapEntry LDAP entry that may contain principal attributes.
     * @return Principal if the LDAP entry contains at least a principal ID attribute value, null otherwise.
     * @throws LoginException On security policy errors related to principal creation.
     */
    protected Principal createPrincipal(final String username, final LdapEntry ldapEntry) throws LoginException {
        final String principalName;
        if (this.principalIdAttribute != null) {
            final LdapAttribute principalAttr = ldapEntry.getAttribute(this.principalIdAttribute);
            if (principalAttr == null || principalAttr.size() == 0) {
                throw new LoginException(this.principalIdAttribute + " attribute not found for " + username);
            }
            if (principalAttr.size() > 1) {
                if (this.allowMultiplePrincipalAttributeValues) {
                    logger.warn(
                            "Found multiple values for principal ID attribute: {}. Using first value={}.",
                            principalAttr,
                            principalAttr.getStringValue());
                } else {
                    throw new LoginException("Multiple principal values not allowed: " + principalAttr);
                }
            }
            principalName = principalAttr.getStringValue();
        } else {
            principalName = username;
        }
        final Map<String, Object> attributeMap = new LinkedHashMap<String, Object>(this.principalAttributeMap.size());
        for (String ldapAttrName : this.principalAttributeMap.keySet()) {
            final LdapAttribute attr = ldapEntry.getAttribute(ldapAttrName);
            if (attr != null) {
                logger.debug("Found principal attribute: {}", attr);
                final String principalAttrName = this.principalAttributeMap.get(ldapAttrName);
                if (attr.size() > 1) {
                    attributeMap.put(principalAttrName, attr.getStringValues());
                } else {
                    attributeMap.put(principalAttrName, attr.getStringValue());
                }
            }
        }
        attributeMap.put("userName", principalName);
        attributeMap.put("dn", ldapEntry.getDn());
        String id = storageLogin.adInfo2DB(attributeMap);
        return new SimplePrincipal(id, attributeMap);
    }

    @PostConstruct
    public void initialize() {
        final List<String> attributes = new ArrayList<String>();
        if (this.principalIdAttribute != null) {
            attributes.add(this.principalIdAttribute);
        }
        attributes.addAll(this.principalAttributeMap.keySet());
        attributes.addAll(this.additionalAttributes);
        this.authenticatedEntryAttributes = attributes.toArray(new String[attributes.size()]);
    }
}
