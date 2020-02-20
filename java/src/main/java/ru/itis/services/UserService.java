package ru.itis.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.UserForm;
import ru.itis.models.User;
import ru.itis.repositories.ChatRepository;
import ru.itis.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public void registration(UserForm userForm) {
        User user = createUser(userForm);
        userRepository.save(user);
    }

    private User createUser(UserForm userForm) {
        return User.builder()
                .username(userForm.getUsername())
                .password(passwordEncoder.encode(userForm.getPassword()))
                .build();
    }

}
