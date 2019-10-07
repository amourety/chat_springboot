package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itis.models.Message;


import java.util.List;


@Repository
public class MessageRepository implements CrudRepository<Message> {

    @Autowired
    private JdbcTemplate template;

    //language=SQL
    private static final String FIND_ALL_100 = "SELECT * FROM lp_messages ORDER BY time DESC LIMIT 15";

    //language=SQL
    private static final String FIND = "SELECT * FROM lp_messages where id = ?";

    //language=SQL
    private static final String SAVE = "INSERT INTO lp_messages(user_id,text) VALUES (?,?)";

    //language=SQL
    private static final String UPDATE = "UPDATE lp_messages SET user_id = ?, text = ? where id = ?";


    private RowMapper<Message> rowMapper = (resultSet, i) -> Message.builder()
            .id(resultSet.getLong("id"))
            .userId(resultSet.getLong("user_id"))
            .text(resultSet.getString("text"))
            .time(resultSet.getTimestamp("time"))
            .build();

    @Override
    public List<Message> findAll() {
        return template.query(FIND_ALL_100,rowMapper);
    }

    @Override
    public Message find(Long id) {
        return null;
    }

    @Override
    public void save(Message model) {
        template.update(SAVE, model.getUserId(),model.getText());
    }

    @Override
    public void update(Message model) {

    }
}
