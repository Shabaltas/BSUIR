package com.bsuir.ashabaltas.piris.model.others;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Table(schema = "piris", name = "disability")
public class Disability {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private long id;
    @Column
    @JsonProperty
    private Integer stage;
}
