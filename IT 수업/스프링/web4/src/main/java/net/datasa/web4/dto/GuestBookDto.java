package net.datasa.web4.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GuestBookDto {

    private Integer boardNum;

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    private String message;

    private LocalDateTime inputDate;
}
