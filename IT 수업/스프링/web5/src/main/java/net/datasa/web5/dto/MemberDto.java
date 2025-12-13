package net.datasa.web5.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MemberDto {


    @NotBlank
    @Size(min = 3, max = 12)
    private String userId;

    @NotBlank
    @Size(min = 5)
    private String password;

    private String updatePassword;
    private String passwordRe;

    @NotBlank
    private String userName;

    private String mailAddress;

    @NotBlank
    private String phoneNumber;

    private String address;

    private String roleName;

    private Byte isActive;

    private LocalDateTime registerDate;

    private String dupChecked;
}
