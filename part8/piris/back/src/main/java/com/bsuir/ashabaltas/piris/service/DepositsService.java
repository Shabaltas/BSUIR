package com.bsuir.ashabaltas.piris.service;

import com.bsuir.ashabaltas.piris.model.client.Client;
import com.bsuir.ashabaltas.piris.model.client.ClientRepository;
import com.bsuir.ashabaltas.piris.model.deposit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.bsuir.ashabaltas.piris.model.deposit.AccountsCode.*;

@Service
public class DepositsService {

    private AccountRepository accountRepository;
    private AccountsPlanRepository accountsPlanRepository;
    private CurrencyRepository currencyRepository;
    private DepositContractRepository depositContractRepository;
    private DepositTypeRepository depositTypeRepository;
    private DepositRepository depositRepository;
    private ClientRepository clientRepository;

    @Autowired
    public DepositsService(AccountRepository accountRepository, AccountsPlanRepository accountsPlanRepository,
                           CurrencyRepository currencyRepository, DepositContractRepository depositContractRepository,
                           DepositTypeRepository depositTypeRepository, DepositRepository depositRepository,
                           ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.accountsPlanRepository = accountsPlanRepository;
        this.currencyRepository = currencyRepository;
        this.depositContractRepository = depositContractRepository;
        this.depositTypeRepository = depositTypeRepository;
        this.depositRepository = depositRepository;
        this.clientRepository = clientRepository;
    }


    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }

    public List<DepositType> getTypes() {
        return depositTypeRepository.findAll();
    }

    public List<Deposit> getDeposits() {
        return depositRepository.findAll();
    }

    public List<AccountPlan> getAccountsPlan() {
        return accountsPlanRepository.findAll();
    }

    public List<BaseAccountResponseDto> getAccounts() {
        return accountRepository.findAll().stream().map(ac -> {
            DepositContract dc = depositContractRepository.getOne(ac.getDepositContractId());
            return new BaseAccountResponseDto(
                    ac.getId(),
                    ac.getNumber(),
                    ac.getClientId(),
                    depositRepository.getOne(dc.getDepositId()).getTitle(),
                    dc.getDepositAmount()
            );
        }).collect(Collectors.toList());
    }

    public List<Deposit> findDepositsByCurAndType(Long currencyId, Long typeId) {
        return depositRepository.findDepositsByCurrencyIdAndTypeId(currencyId, typeId);
    }

    public List<BaseAccountResponseDto> getAccountsForClient(Long clientId) {
        List<Account> accounts = accountRepository.findAccountsByClientId(clientId);
        return accounts.stream().map(ac -> {
            DepositContract dc = depositContractRepository.getOne(ac.getDepositContractId());
            return new BaseAccountResponseDto(
                    ac.getId(),
                    ac.getNumber(),
                    ac.getClientId(),
                    depositRepository.getOne(dc.getDepositId()).getTitle(),
                    dc.getDepositAmount()
            );
        }).collect(Collectors.toList());
    }

    public AccountResponseDto getAccount(Long accountId) {
        Account acc = accountRepository.getOne(accountId);
        AccountResponseDto dto = new AccountResponseDto();
        dto.setAccountId(acc.getId());
        dto.setAccountNumber(acc.getNumber());
        dto.setClientId(acc.getClientId());
        Client client = clientRepository.getOne(acc.getClientId());
        dto.setClientSurname(client.getSurname());
        dto.setClientName(client.getName());
        dto.setClientPatronymic(client.getSecond_name());
        DepositContract dc = depositContractRepository.getOne(acc.getDepositContractId());
        Deposit d = depositRepository.getOne(dc.getDepositId());
        dto.setCurrentAmount(dc.getDepositAmount());
        dto.setCurrency(currencyRepository.getOne(d.getCurrencyId()).getCurrency());
        dto.setDepositType(depositTypeRepository.getOne(d.getTypeId()).getType());
        dto.setTermInMonth(d.getTermInMonth());
        dto.setInterestOnDeposit(d.getInterestOnDeposit());
        dto.setContractNumber(dc.getNumber());
        dto.setStartDate(dc.getDepositStartDate());
        dto.setEndDate(dc.getDepositEndDate());
        return dto;
    }

    public void openDeposit(FixedTermContractDto dto) {
        double amount = dto.getDepositAmount();
        String contractNumber = clientRepository.getOne(dto.getClientId()).getPassport_series()
                + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
                + (depositContractRepository.count() + 1);
        DepositContract dc = new DepositContract(contractNumber, dto.getDepositId(), dto.getStartDate(),
                dto.getEndDate(), amount, dto.getClientId(), dto.getCapitalization());
        dc = depositContractRepository.save(dc);
        Account debAcc = createAccount(DEP.getCode(), amount, dc.getId(), dto.getClientId());
        Account prcAcc = createAccount(PRC.getCode(), 0.0, dc.getId(), dto.getClientId());
        Account bdf = accountRepository.findAccountsByCode(BDF.getCode()).get(0);
        Account cash = accountRepository.findAccountsByCode(CASH.getCode()).get(0);
        cash.addDebet(amount);
        cash.addCredit(amount);
        bdf.addCredit(amount);
        accountRepository.save(bdf);
        accountRepository.save(cash);
        depositContractRepository.flush();
        accountRepository.flush();
    }

    public void closeDay() {
        List<Account> depAccounts = accountRepository.findAccountsByCode(DEP.getCode());
        List<Account> prcAccounts = accountRepository.findAccountsByCode(PRC.getCode());
        Account bdf = accountRepository.findAccountsByCode(BDF.getCode()).get(0);
        Account cash = accountRepository.findAccountsByCode(CASH.getCode()).get(0);
        depAccounts.forEach(depAcc -> {
            DepositContract dc = depositContractRepository.getOne(depAcc.getDepositContractId());
            Deposit d = depositRepository.getOne(dc.getDepositId());
            Account prcAcc = prcAccounts.stream().filter(ac -> ac.getDepositContractId() == dc.getId()).findFirst().get();

            boolean capitalization = dc.getCapitalization();

            if (dc.getDepositEndDate().toLocalDate().compareTo(LocalDate.now()) == 0) {
                //окончание договора
                if (capitalization) {
                    depAcc.addDebet(depAcc.getSaldo());
                    cash.addDebet(depAcc.getSaldo());
                    cash.addCredit(depAcc.getSaldo());
                } else {
                    prcAcc.addDebet(prcAcc.getSaldo());
                    cash.addDebet(prcAcc.getSaldo());
                    cash.addCredit(prcAcc.getSaldo());
                }
                bdf.addDebet(dc.getDepositAmount());
                depAcc.addCredit(dc.getDepositAmount());
                depAcc.addDebet(dc.getDepositAmount());
                cash.addCredit(dc.getDepositAmount());
                cash.addDebet(dc.getDepositAmount());
            } else if (Period.between(dc.getDepositStartDate().toLocalDate(), LocalDate.now()).getDays() == 0 && capitalization) {
                //ежемесячная(календарный месяц) капитализация
                depAcc.addCredit(prcAcc.getSaldo());
                prcAcc.addDebet(prcAcc.getSaldo());
            } else {
                //ежедневное начисление процента
                double prcAmount = (dc.getDepositAmount() + depAcc.getSaldo()) * d.getInterestOnDeposit() * 1 / 100 / 365;
                bdf.addDebet(prcAmount);
                prcAcc.addCredit(prcAmount);
            }
            accountRepository.saveAll(List.of(depAcc, prcAcc, bdf, cash));
            accountRepository.flush();
        });
    }

    private Account createAccount(int code, double depositAmount, long contractId, long clientId) {
        String between = String.format("%1$8s", accountRepository.count()).replace(' ', '0');
        String accountNumber = code + between;
        int last = getControlKey(accountNumber);
        accountNumber += last;
        Account acc = new Account(accountNumber, code, depositAmount,
                depositAmount, 0, contractId, clientId);
        return accountRepository.save(acc);
    }

    private int getControlKey(String accNumber) {
        int[] koeffs = new int[] {713, 371, 371, 371, 371};
        String[] parts = ("111" + accNumber).split("(?<=\\G.{3})");
        int sum = IntStream.range(0, parts.length).map(i -> (Integer.parseInt(parts[i]) * koeffs[i]) % 10).reduce(Integer::sum).getAsInt();
        return (3 * sum) % 10;
    }
}
