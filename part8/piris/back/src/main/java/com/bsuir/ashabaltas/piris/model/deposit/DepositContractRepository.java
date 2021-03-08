package com.bsuir.ashabaltas.piris.model.deposit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepositContractRepository extends JpaRepository<DepositContract, Long> {

    @Query("select d from DepositContract d where d.clientId=?1")
    public List<DepositContract> findContractsByClientId(Long clientId);
}
