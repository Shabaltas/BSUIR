package com.bsuir.ashabaltas.piris.model.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
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
    private Integer city_of_residence;
    @Column
    @JsonProperty
    private String address_of_residence;
    @Column
    @JsonProperty
    private String home_phone;
    @Column
    @JsonProperty
    private String mobile_phone;
    @Column
    @JsonProperty
    private String email;
    @Column
    @JsonProperty
    private String place_of_work;
    @Column
    @JsonProperty
    private String position;
    @Column
    @JsonProperty
    private Integer marital_status;
    @Column
    @JsonProperty
    private Integer citizenship;
    @Column
    @JsonProperty
    private Integer disability;
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
