package com.bsuir.ashabaltas.piris.model.deposit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(schema = "piris", name = "deposit_contract")
public class DepositContract {
    public DepositContract(String number, long depositId, Date depositStartDate, Date depositEndDate,
                           double depositAmount, long clientId, boolean capitalization) {
        this.number = number;
        this.depositId = depositId;
        this.depositStartDate = depositStartDate;
        this.depositEndDate = depositEndDate;
        this.depositAmount = depositAmount;
        this.clientId = clientId;
        this.capitalization = capitalization;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private long id;

    @Column
    @JsonProperty
    //passport seria + data + id
    private String number;

    @Column(name = "deposit_id")
    @JsonProperty
    private long depositId;

    @Column(name = "deposit_start_date")
    @JsonProperty
    private Date depositStartDate;

    @Column(name = "deposit_end_date")
    @JsonProperty
    private Date depositEndDate;

    @Column(name = "deposit_amount")
    @JsonProperty
    private double depositAmount;

    @Column(name = "client_id")
    @JsonProperty
    private long clientId;

    @Column
    @JsonProperty
    private Boolean capitalization;
}
