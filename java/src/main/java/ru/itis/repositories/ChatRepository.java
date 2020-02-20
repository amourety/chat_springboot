package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itis.models.Location;

import java.util.List;

@Repository
public class ChatRepository implements CrudRepository<Location> {

    @Autowired
    private JdbcTemplate template;

    private RowMapper<Location> roomRowMapper = (resultSet, i) ->
            Location.builder()
                    .id(resultSet.getLong("id"))
                    .username(resultSet.getString("username"))
                    .chatId(resultSet.getLong("chatid"))
                    .build();

    @Override
    public List<Location> findAll() {
        return null;
    }

    @Override
    public Location find(Long id) {
        //
        return null;
    }

    public Location findByUsername(String username) {
        //language=SQL
        String SQL = "SELECT * FROM chat_room WHERE username = ?";
        try {
            return template.queryForObject(SQL, roomRowMapper, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Location> findAllByChat(Long chatId) {
        //language=SQL
        String SQL = "SELECT * FROM chat_room where chatid = ?";
        return template.query(SQL, roomRowMapper, chatId);
    }

    @Override
    public void save(Location model) {
        //language=SQL
        String SQL = "INSERT INTO chat_room(username, chatid) VALUES (?,?)";
        template.update(SQL, model.getUsername(), model.getChatId());
    }

    @Override
    public void update(Location model) {
        //language=SQL
        String SQL = "UPDATE chat_room set chatid = ? WHERE username = ?";
        template.update(SQL, model.getChatId(), model.getUsername());
    }

    public Location findAliveChat() {
        //language=SQL
        String SQL = "SELECT * FROM chat_room where chatid is not NULL LIMIT 1";
        try {
            return template.queryForObject(SQL, roomRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
