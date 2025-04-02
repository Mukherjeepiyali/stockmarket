package com.example.MongoSpring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.MongoSpring.model.DematAccount;
import java.util.Optional;

public interface DematAccountRepository extends MongoRepository<DematAccount, String> {
    Optional<DematAccount> findByPanNumber(String panNumber);
}
