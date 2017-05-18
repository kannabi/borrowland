package com.lackhite.borrowland.server.repositories;

import com.lackhite.borrowland.server.domain.ActiveLoan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActiveLoanRepository extends MongoRepository<ActiveLoan, String> {
    List<ActiveLoan> findAll();
    ActiveLoan findById(@Param("id") Long id);
}
