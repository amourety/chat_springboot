package ru.itis.dto;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


@Data
@Builder
public class MessageDto {
    private String username;
    private String text;
    private String time;
    private Long chatId;

    public void addTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        setTime(dateFormat.format(new Timestamp(System.currentTimeMillis())));
    }
}