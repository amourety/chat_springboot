package ru.itis.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Token {

    private Long id;
    private String value;

    private Long userId;

    private LocalDateTime createdAt;
    private LocalDateTime expiredDateTime;

    public boolean isNotExpired() {
        return LocalDateTime.now().isBefore(expiredDateTime);
    }

}
