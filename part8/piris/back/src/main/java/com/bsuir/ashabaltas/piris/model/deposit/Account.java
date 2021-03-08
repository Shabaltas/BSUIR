package com.bsuir.ashabaltas.piris.model.deposit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(schema = "piris", name = "account")
public class Account {
    public Account(String number, int code, double debet, double credit, double saldo, long depositContractId, long clientId) {
        this.number = number;
        this.code = code;
        this.debet = debet;
        this.credit = credit;
        this.saldo = saldo;
        this.depositContractId = depositContractId;
        this.clientId = clientId;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private long id;

    @Column
    @JsonProperty
    private String number;

    @Column
    @JsonProperty
    private int code;

    @Column
    @JsonProperty
    private double debet;

    @Column
    @JsonProperty
    private double credit;

    @Column
    @JsonProperty
    private double saldo;

    @Column(name = "deposit_contract_id")
    @JsonProperty
    private long depositContractId;

    @Column(name = "client_id")
    @JsonProperty
    private long clientId;

    public void addDebet(double diff) {
        debet += diff;
        saldo = credit - debet;
    }

    public void addCredit(double diff) {
        credit += diff;
        saldo = credit - debet;
    }
}
