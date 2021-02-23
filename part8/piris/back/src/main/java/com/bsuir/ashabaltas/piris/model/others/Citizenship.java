package com.bsuir.ashabaltas.piris.model.others;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Setter
@Table(schema = "piris", name = "citizenship")
public class Citizenship {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private long id;
    @Column
    @JsonProperty
    private String country;
}
