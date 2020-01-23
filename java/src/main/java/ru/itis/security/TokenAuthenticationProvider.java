package ru.itis.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsServiceImpl service;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        TokenAuthentication tokenAuthentication
                = (TokenAuthentication)authentication;
        UsersDetailsImpl userDetails = (UsersDetailsImpl) service.loadUserByUsernameByToken(tokenAuthentication.getName());
        if (userDetails != null) {
            tokenAuthentication.setUserDetails(userDetails);
            tokenAuthentication.setAuthenticated(true);
        } else {
            throw new BadCredentialsException("Incorrect Token");
        }
        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}