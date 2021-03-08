package com.bsuir.ashabaltas.piris.model.client;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;
import java.time.LocalDate;

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
    @Pattern(regexp = "[A-Z]{2,5}", message = "Passport series")
    private String passport_series;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9]{7,10}", message = "Phone Number")
    private String passport_number;
    @NotNull
    @NotEmpty
    private String issued_by;
    @NotNull
    @Past(message = "DATE OF ISSUE SUCKS")
    private Date date_of_issue;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9A-Z]+", message = "IDENT NUMBER") //TODO
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
    @Pattern(regexp = "\\d{7}", message = "Please provide a valid home phone")
    private String home_phone;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\d{9}", message = "Please provide a valid mobile phone")
    private String mobile_phone;
    @NotNull
    @Email
    private String email;
    private String place_of_work;
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
