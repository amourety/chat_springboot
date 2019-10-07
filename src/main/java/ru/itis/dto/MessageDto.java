package ru.itis.dto;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class MessageDto {
    private String text;
    private String username;
    private String time;
}