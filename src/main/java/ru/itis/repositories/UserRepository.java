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
    private static final String FIND_ALL = "SELECT * FROM lp_user";

    //language=SQL
    private static final String FIND = "SELECT * FROM lp_user where id = ?";

    //language=SQL
    private static final String SAVE = "INSERT INTO lp_user(username,password) VALUES (?,?)";

    //language=SQL
    private static final String UPDATE = "UPDATE lp_user SET username = ?, password = ? where id = ?";


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
        String SQL = "SELECT user2.id, user2.username,user2.password, token2.id as token_id, token2.value, token2.user_id FROM lp_user user2 join lp_token token2 on user2.id  = token2.user_id where user2.username = ?";
        try {
            return template.queryForObject(SQL, rowMapperToken, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public User findByUsername(String username) {
        //language=SQL
        String SQL = "SELECT * FROM lp_user user2 where user2.username = ?";
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
        String SQL = "SELECT user2.id, user2.username,user2.password, token2.id as token_id, token2.value, token2.user_id FROM lp_user user2 join lp_token token2 on user2.id  = token2.user_id where token2.value = ?";
        try {
            return template.queryForObject(SQL, rowMapperToken, token);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
