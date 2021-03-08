package com.bsuir.ashabaltas.piris.controller;

import com.bsuir.ashabaltas.piris.model.deposit.*;
import com.bsuir.ashabaltas.piris.service.DepositsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@ResponseStatus(HttpStatus.OK)
@RequestMapping(path = "/deposits", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepositsController {
    private DepositsService depositsService;
    @Autowired
    public DepositsController(DepositsService service) {
        this.depositsService = service;
    }

    @GetMapping("/currencies")
    public List<Currency> getCurrencies() {
        return depositsService.getCurrencies();
    }

    @GetMapping("/types")
    public List<DepositType> getTypes() {
        return depositsService.getTypes();
    }

    @GetMapping
    public List<Deposit> getDeposits() {
        return depositsService.getDeposits();
    }

    @GetMapping("/accounts_plan")
    public List<AccountPlan> getAccountsPlan() {
        return depositsService.getAccountsPlan();
    }

    @GetMapping(params = {"currency_id", "type_id"})
    public List<Deposit> getDepositsByCurrencyAndType(@RequestParam("currency_id") Long currencyId, @RequestParam("type_id") Long typeId) {
        return depositsService.findDepositsByCurAndType(currencyId, typeId);
    }

    @GetMapping("/accounts")
    public List<BaseAccountResponseDto> getAccounts() {
        return depositsService.getAccounts();
    }

    @GetMapping("/accounts/{clientId}")
    public List<BaseAccountResponseDto> getAccountsForClient(@PathVariable Long clientId) {
        return depositsService.getAccountsForClient(clientId);
    }

    @GetMapping("/accounts/{accountId}")
    public AccountResponseDto getAccount(@PathVariable Long accountId) {
        return depositsService.getAccount(accountId);
    }

    @PostMapping("/new")
    public void openDeposit(@Valid @RequestBody FixedTermContractDto dto) {
        depositsService.openDeposit(dto);
    }

    @PostMapping("/close_day")
    public void closeDay() {
        depositsService.closeDay();
    }
}
