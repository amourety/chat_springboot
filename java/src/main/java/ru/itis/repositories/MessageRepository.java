package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itis.dto.MessageDto;
import ru.itis.models.Message;

import java.sql.Timestamp;
import java.util.List;


@Repository
public class MessageRepository implements CrudRepository<Message> {

    @Autowired
    private JdbcTemplate template;

    //language=SQL
    private static final String FIND_ALL_30 = "SELECT * FROM messages ORDER BY time DESC LIMIT 50";

    //language=SQL
    private static final String FIND = "SELECT * FROM messages where id = ?";

    //language=SQL
    private static final String FIND_ALL_AS_STRING = "SELECT * FROM messages m join users u on m.user_id = u.id where m.chat_id = ? ORDER BY time DESC LIMIT 50;";

    //language=SQL
    private static final String SAVE = "INSERT INTO messages(user_id,text,time) VALUES (?,?,?)";

    //language=SQL
    private static final String SAVEWS = "INSERT INTO messages(user_id,text,time,chat_id) SELECT u.id,?,?,? from users u where u.username = ?";

    //language=SQL
    private static final String UPDATE = "UPDATE messages SET user_id = ?, text = ? where id = ?";

    private RowMapper<Message> rowMapper = (resultSet, i) -> Message.builder()
            .id(resultSet.getLong("id"))
            .userId(resultSet.getLong("user_id"))
            .text(resultSet.getString("text"))
            .time(resultSet.getTimestamp("time"))
            .userId(resultSet.getLong("chat_id"))
            .build();

    private RowMapper<MessageDto> rowMapperMD = (resultSet, i) -> MessageDto.builder()
            .username(resultSet.getString("username"))
            .text(resultSet.getString("text"))
            .time(resultSet.getString("time"))
            .chatId(resultSet.getLong("chat_id"))
            .build();

    @Override
    public List<Message> findAll() {
        return template.query(FIND_ALL_30, rowMapper);
    }

    public List<MessageDto> findMessageAsString(Long id) {
        return template.query(FIND_ALL_AS_STRING, rowMapperMD, id);
    }

    @Override
    public Message find(Long id) {
        return null;
    }

    @Override
    public void save(Message model) {
        template.update(SAVE, model.getUserId(), model.getText(), new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void update(Message model) {

    }

    public void saveWS(MessageDto model) {
        template.update(SAVEWS, model.getText(), new Timestamp(System.currentTimeMillis()), model.getChatId(), model.getUsername());
    }
}
