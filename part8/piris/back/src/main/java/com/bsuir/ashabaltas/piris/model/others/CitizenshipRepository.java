package com.bsuir.ashabaltas.piris.model.others;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenshipRepository extends JpaRepository<Citizenship, Long> {
}
