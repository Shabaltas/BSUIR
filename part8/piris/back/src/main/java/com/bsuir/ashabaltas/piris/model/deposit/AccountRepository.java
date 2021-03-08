package com.bsuir.ashabaltas.piris.model.deposit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a where a.clientId=?1")
    public List<Account> findAccountsByClientId(Long clientId);

    @Query("select a from Account a where a.code=?1")
    public List<Account> findAccountsByCode(Integer code);
}
