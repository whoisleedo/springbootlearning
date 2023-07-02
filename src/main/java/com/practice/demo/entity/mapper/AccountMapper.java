package com.practice.demo.entity.mapper;

import com.practice.demo.entity.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;
@Mapper
public interface AccountMapper {
    Optional<Account> selectUserByAccount(String account);
}
