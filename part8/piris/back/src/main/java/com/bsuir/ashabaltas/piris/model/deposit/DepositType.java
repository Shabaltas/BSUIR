package com.bsuir.ashabaltas.piris.model.deposit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(schema = "piris", name = "deposit_type")
public class DepositType {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private long id;

    @Column
    @JsonProperty
    private String type;
}
