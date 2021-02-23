package com.bsuir.ashabaltas.piris.model.client;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
//DTO from front for creating new client
public class ClientDto {
    @NotNull
    @NotEmpty
    private String surname;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String second_name;
    @NotNull
    @Past
    private Date date_of_birth;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[A-Z]{2,5}")
    private String passport_series;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9]{7,10}")
    private String passport_number;
    @NotNull
    @NotEmpty
    private String issued_by;
    @NotNull
    @Past
    private Date date_of_issue;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9,A-Z]{25}") //TODO
    private String identification_number;
    @NotNull
    @NotEmpty
    private String place_of_birth;
    @NotNull
    private Integer city_of_residence; // Integer???
    @NotNull
    @NotEmpty
    private String address_of_residence;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9]{7}")
    private String home_phone;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9]{9}")
    private String mobile_phone;
    @NotNull
    @Email
    private String email;
    @NotNull
    @NotEmpty
    private String place_of_work;
    @NotNull
    @NotEmpty
    private String position;
    @NotNull
    private Integer marital_status;
    @NotNull
    private Integer citizenship;
    private Integer disability;
    @NotNull
    private Boolean retiree;
    @NotNull
    @Positive
    private Double monthly_income;
    @NotNull
    private Boolean liable_for_military;
}
