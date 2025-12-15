package net.datasa.web5.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {

    private Integer boardNum;

    private String userId;
    private String userName;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private Integer viewCount;
    private Integer likeCount;
    private String originalName;
    private String fileName;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
