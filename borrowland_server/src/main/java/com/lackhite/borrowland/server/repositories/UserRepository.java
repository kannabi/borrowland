package com.lackhite.borrowland.server.repositories;

import com.lackhite.borrowland.server.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends MongoRepository<User, String> {
    User findById(@Param("id") Long id);

}
