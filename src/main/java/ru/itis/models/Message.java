package ru.itis.models;


import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class Message {

    private Long id;
    private Long userId;
    private String text;
    private Timestamp time;
}
