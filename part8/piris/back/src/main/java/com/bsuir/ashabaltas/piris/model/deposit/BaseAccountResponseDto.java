package com.bsuir.ashabaltas.piris.model.deposit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseAccountResponseDto {
    private Long id;
    private String accountNumber;
    private Long clientId;
    private String depositName;
    private double credit;
    private double debet;
}
