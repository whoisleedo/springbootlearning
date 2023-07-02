package com.pratice.demo.entity.repository;

import com.pratice.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    boolean existsByUserAccount(String account);
}
