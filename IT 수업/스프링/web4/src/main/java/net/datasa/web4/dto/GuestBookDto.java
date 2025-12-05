package net.datasa.web4.dto;

import jakarta.validation.constraints.NotBlank;
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

    private Integer recommend;

    private Integer reportCount;

    private String userIp;

    private LocalDateTime inputDate;

    private LocalDateTime updateDate;
}
