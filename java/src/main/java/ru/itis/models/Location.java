package ru.itis.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
    private Long id;
    private String username;
    private Long chatId;
}
