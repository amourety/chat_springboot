package ru.itis.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.models.User;
import ru.itis.repositories.UserRepository;

@Component(value = "customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException, DataAccessException {
        User user = userRepository.findByUsernameToken(name);
        if (user != null){
            return new UsersDetailsImpl(user, user.getToken());
        }
        return null;
    }

    public UserDetails loadUserByUsernameByToken(String token) {
        User user = userRepository.findUserByToken(token);
        if (user != null){
            return new UsersDetailsImpl(user, user.getToken());
        }
        return null;
    }
}