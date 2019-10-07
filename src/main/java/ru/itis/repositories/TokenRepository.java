package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itis.models.Token;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TokenRepository implements CrudRepository<Token> {

    @Autowired
    private JdbcTemplate template;

    private RowMapper<Token> rowMapper = (resultSet, i) -> Token.builder()
            .id(resultSet.getLong("id"))
            .value(resultSet.getString("value"))
            .userId(resultSet.getLong("user_id"))
            .createdAt(resultSet.getTimestamp("start_time").toLocalDateTime())
            .expiredDateTime(resultSet.getTimestamp("end_time").toLocalDateTime())
            .build();
    @Override
    public List<Token> findAll() {
        return null;
    }

    @Override
    public Token find(Long id) {
        return null;
    }


    @Override
    public void save(Token model) {
        //language=SQL
        String SQL = "INSERT INTO lp_token (value, user_id, start_time,end_time) VALUES (?,?,?,?)";
        //language=SQL
        String SQL2 = "DELETE from lp_token where user_id = ?";
        template.update(SQL2, model.getUserId());
        template.update(SQL, model.getValue(),model.getUserId(), Timestamp.valueOf(model.getCreatedAt()),Timestamp.valueOf(model.getExpiredDateTime()));
    }

    @Override
    public void update(Token model) {

    }

    public void deleteTokensByExpiredDateTimeBefore(LocalDateTime now) {
        //language=SQL
        String SQL = "DELETE from lp_token where end_time >= ?";
        template.update(SQL, Timestamp.valueOf(now));
    }
}
