package com.bsuir.ashabaltas.piris.model.deposit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(schema = "piris", name = "deposit")
public class Deposit {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private long id;

    @Column
    @JsonProperty
    private String title;

    @Column(name = "type_id")
    @JsonProperty
    private long typeId;

    @Column(name = "currency_id")
    @JsonProperty
    private long currencyId;

    @Column(name = "term_in_month")
    @JsonProperty
    private int termInMonth;

    @Column(name = "interest_on_deposit")
    @JsonProperty
    private float interestOnDeposit;
}
