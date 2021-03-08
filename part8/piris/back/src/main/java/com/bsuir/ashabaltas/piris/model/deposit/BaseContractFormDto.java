package com.bsuir.ashabaltas.piris.model.deposit;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Date;

@Getter
@Setter
public abstract class BaseContractFormDto {

    @NotNull
    @Positive
    private Long depositId;

    @NotNull
    @Positive
    private Long depositTypeId;

    @NotNull
    @Positive
    private Long currencyId;

    @NotNull
    @FutureOrPresent
    private Date startDate;

    @NotNull
    @Positive
    private Double depositAmount;

    @NotNull
    @Positive
    private Double interestOnDeposit;

    @NotNull
    @Positive
    private Long clientId;

    @NotNull
    private Boolean capitalization;
}
