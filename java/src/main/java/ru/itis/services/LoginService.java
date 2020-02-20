package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.LoginDto;
import ru.itis.dto.TokenDto;
import ru.itis.models.Token;
import ru.itis.models.User;
import ru.itis.repositories.TokenRepository;
import ru.itis.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public TokenDto authenticate(LoginDto loginData) {
        User userCandidate = userRepository.findByUsername(loginData.getLogin());
        if (userCandidate != null) {
            if (validatePassword(loginData.getPassword(), userCandidate.getPassword())) {
                return createToken(userCandidate);
            }
        }
        throw new BadCredentialsException("Incorrect login or password");
    }

    private boolean validatePassword(String password, String hashPassword) {
        return passwordEncoder.matches(password, hashPassword);
    }

    private TokenDto createToken(User userCandidate) {
        Token token = generateToken(userCandidate);
        tokenRepository.save(token);
        return TokenDto.from(token.getValue());
    }

    public String validationByToken(String value) {
        User user = userRepository.findUserByTokenWithoutData(value);
        return user != null ? "valid" : "invalid";
    }

    private Token generateToken(User userCandidate) {
        return Token.builder()
                .value(UUID.randomUUID().toString())
                .userId(userCandidate.getId())
                .createdAt(LocalDateTime.now())
                .expiredDateTime(LocalDateTime.now().plusSeconds(10000))
                .build();
    }
}
