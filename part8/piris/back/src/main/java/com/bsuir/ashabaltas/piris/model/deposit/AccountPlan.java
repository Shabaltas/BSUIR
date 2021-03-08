package com.bsuir.ashabaltas.piris.model.deposit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Entity
@Table(schema = "piris", name = "accounts_plan")
public class AccountPlan {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private long id;

    @Column
    @JsonProperty
    private int code;

    @Column
    @JsonProperty
    private String title;

    @Column
    @JsonProperty
    private String activity;
}
