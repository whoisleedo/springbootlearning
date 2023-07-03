package com.practice.demo.entity.repository;

import com.practice.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    boolean existsByUserAccount(String account);
    Optional<Account> findByUserAccount(String userAccount);
}
