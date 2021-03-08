package com.bsuir.ashabaltas.piris.model.deposit;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Date;

@Getter
@Setter
public class FixedTermContractDto extends BaseContractFormDto{
    @NotNull
    @Future
    private Date endDate;

    @NotNull
    @Positive
    private Integer termInMonth;
}
