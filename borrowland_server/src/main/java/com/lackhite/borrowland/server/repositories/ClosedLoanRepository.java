package com.lackhite.borrowland.server.repositories;

import com.lackhite.borrowland.server.domain.ClosedLoan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClosedLoanRepository extends MongoRepository<ClosedLoan, String> {
    List<ClosedLoan> findAll();
}
