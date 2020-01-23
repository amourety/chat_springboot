package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itis.models.Token;
import ru.itis.models.User;

import java.util.List;


@Repository
public class UserRepository implements CrudRepository<User> {
    @Autowired
    private JdbcTemplate template;


    //language=SQL
    private static final String FIND_ALL = "SELECT * FROM users";

    //language=SQL
    private static final String FIND = "SELECT * FROM users where id = ?";

    //language=SQL
    private static final String SAVE = "INSERT INTO users(username,password) VALUES (?,?)";

    //language=SQL
    private static final String UPDATE = "UPDATE users SET username = ?, password = ? where id = ?";


    private RowMapper<User> rowMapperToken = (resultSet, i) -> User.builder()
            .id(resultSet.getLong("id"))
            .username(resultSet.getString("username"))
            .password(resultSet.getString("password"))
            .token(Token.builder()
                    .id(resultSet.getLong("token_id"))
                    .value(resultSet.getString("value"))
                    .userId(resultSet.getLong("user_id"))
                    .build())
            .build();

    private RowMapper<User> rowMapper = (resultSet, i) -> User.builder()
            .id(resultSet.getLong("id"))
            .username(resultSet.getString("username"))
            .password(resultSet.getString("password"))
            .build();
    private RowMapper<User> validateRowMapper = (resultSet, i) -> User.builder()
            .username(resultSet.getString("username"))
            .build();



    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User find(Long id) {
        try {
            return template.queryForObject(FIND, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User findByUsernameToken(String username) {
        //language=SQL
        String SQL = "SELECT u.id, u.username,u.password, token2.id as token_id, token2.value, token2.user_id FROM users u join tokens token2 on u.id  = token2.user_id where u.username = ?";
        try {
            return template.queryForObject(SQL, rowMapperToken, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public User findByUsername(String username) {
        //language=SQL
        String SQL = "SELECT * FROM users u where u.username = ?";
        try {
            return template.queryForObject(SQL, rowMapper, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void save(User model) {
        template.update(SAVE, model.getUsername(), model.getPassword());
    }

    @Override
    public void update(User model) {

    }

    public User findUserByToken(String token) {
        //language=SQL
        String SQL = "SELECT u.id, u.username,u.password, token2.id as token_id, token2.value, token2.user_id FROM users u join tokens token2 on u.id  = token2.user_id where token2.value = ?";
        try {
            return template.queryForObject(SQL, rowMapperToken, token);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public User findUserByTokenWithoutData(String token){
        //language=SQL
        String SQL = "SELECT u.id, u.username FROM users u join tokens token2 on u.id  = token2.user_id where token2.value = ?";
        try {
            return template.queryForObject(SQL, validateRowMapper, token);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
