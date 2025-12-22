package net.datasa.web5.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyDto {

    private Integer replyNum;

    @NotNull
    private Integer boardNum;
    private String userId;

    @NotBlank
    private String content;
    private LocalDateTime createTime;

}
