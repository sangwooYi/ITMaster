package net.datasa.web3.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="student")
public class StudentEntity {

    @Id
    @Column(nullable = false)
    String number;

    @Column
    String name;

    @Column
    Integer korean;

    @Column
    Integer english;

    @Column
    Integer math;

}
