package com.bsuir.ashabaltas.piris.model.deposit;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Getter
@Setter
public abstract class BaseContractFormDto {

    @NotNull
    @Positive
    private Long deposit;

    @NotNull
    @Positive
    private Long depositType;

    @NotNull
    @Positive
    private Long currency;

    @NotNull
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
