package com.bsuir.ashabaltas.piris.model.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Setter
@Entity
@Table(schema = "piris", name = "clients")
public class Client {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private long id;
    @Column
    @JsonProperty
    private String surname;
    @Column
    @JsonProperty
    private String name;
    @Column
    @JsonProperty
    private String second_name;
    @Column
    @JsonProperty
    private Date date_of_birth;
    @Column
    @JsonProperty
    private String passport_series;
    @Column
    @JsonProperty
    private String passport_number;
    @Column
    @JsonProperty
    private String issued_by;
    @Column
    @JsonProperty
    private Date date_of_issue;
    @Column
    @JsonProperty
    private String identification_number;
    @Column
    @JsonProperty
    private String place_of_birth;
    @Column
    @JsonProperty
    private Long cityOfResidence; // int???
    @Column
    @JsonProperty
    private String addressOfResidence;
    @Column
    @JsonProperty
    private String homePhone;
    @Column
    @JsonProperty
    private String mobilePhone;
    @Column
    @JsonProperty
    private String email;
    @Column
    @JsonProperty
    private String placeOfWork;
    @Column
    @JsonProperty
    private String position;
    @Column
    @JsonProperty
    private Long maritalStatus;
    @Column
    @JsonProperty
    private Long citizenship;
    @Column
    @JsonProperty
    private Long disability;
    @Column
    @JsonProperty
    private boolean retiree;
    @Column
    @JsonProperty
    private double monthly_income;
    @Column
    @JsonProperty
    private boolean liable_for_military;
}
