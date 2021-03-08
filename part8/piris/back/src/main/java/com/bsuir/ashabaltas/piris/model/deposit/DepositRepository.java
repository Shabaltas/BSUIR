package com.bsuir.ashabaltas.piris.model.deposit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

    @Query("select d from Deposit d where d.currencyId=?1 and d.typeId=?2")
    public List<Deposit> findDepositsByCurrencyIdAndTypeId(Long currencyId, Long typeId);
}
