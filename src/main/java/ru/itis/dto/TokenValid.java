package ru.itis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenValid {
    private String valid;
}
