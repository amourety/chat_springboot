package ru.itis.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.itis.services.ChatService;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsServiceImpl service;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // делаем явное преобразование для работы с TokenAuthentication
        TokenAuthentication tokenAuthentication
                = (TokenAuthentication)authentication;
        // загружаем данные безопасности пользователя из UserDetailsService
        // по токену достали пользователя из БД
        UsersDetailsImpl userDetails = (UsersDetailsImpl) service.loadUserByUsernameByToken(tokenAuthentication.getName());
        // если данные пришли
        if (userDetails != null) {
            // в данный объект аутентификации кладем пользователя
            tokenAuthentication.setUserDetails(userDetails);
            // говорим, что с ним все окей
            tokenAuthentication.setAuthenticated(true);
        } else {
            throw new BadCredentialsException("Incorrect Token");
        }
        // возвращаем объект SecurityContext-у
        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}