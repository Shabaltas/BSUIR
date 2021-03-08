package com.bsuir.ashabaltas.piris.model.deposit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountResponseDto extends BaseAccountResponseDto{
    private String clientSurname;
    private String clientName;
    private String clientPatronymic;

    private String currency;
    private String depositType;
    private int termInMonth;
    private float interestOnDeposit;

    private String contractNumber;
    private Date startDate;
    private Date endDate;

    public AccountResponseDto(Long accountId, String accountNumber,
                              Long clientId, String depositName,
                              double currentAmount, String clintSurname,
                              String clientName, String clientPatronymic,
                              String currency, String depositType,
                              short termInMonth, float interestOnDeposit,
                              String contractNumber, Date startDate, Date endDate) {
        super(accountId, accountNumber, clientId, depositName, currentAmount);
        this.clientSurname = clintSurname;
        this.clientName = clientName;
        this.clientPatronymic = clientPatronymic;
        this.currency = currency;
        this.depositType = depositType;
        this.termInMonth = termInMonth;
        this.interestOnDeposit = interestOnDeposit;
        this.contractNumber = contractNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
