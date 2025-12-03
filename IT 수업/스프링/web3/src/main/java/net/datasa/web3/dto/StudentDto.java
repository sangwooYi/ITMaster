package net.datasa.web3.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
public class StudentDto {

    public String number;
    public String name;
    public Integer korean;
    public Integer english;
    public Integer math;

}
