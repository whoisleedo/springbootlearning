package com.practice.demo.sevice;

import com.practice.demo.dto.AccountDto;
import com.practice.demo.dto.LoginDto;
import com.practice.demo.dto.UserDto;
import com.practice.demo.entity.Account;
import com.practice.demo.entity.mapper.AccountMapper;
import com.practice.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountMyBatisService {
    private final AccountMapper accountMapper;
    @Autowired
    public AccountMyBatisService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public Optional<UserDto> getUserByAccount(String account){
        return accountMapper.selectUserByAccount(account).map(this::convertUserVo);
    }

    public Optional<String> login(LoginDto loginDto){
        String account = loginDto.getAccount();
        return accountMapper.selectUserByAccount(account)
                .filter(user -> {
                    return BCrypt.checkpw(loginDto.getPassword(),user.getPassword());
                }).map(JwtUtil::generateToken);
    }


    private UserDto convertUserVo(Account account){
        Long id = account.getId();
        String name = account.getName();
        String userAccount = account.getUserAccount();
        String email = account.getEmail();

        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setAccount(userAccount);
        userDto.setEmail(email);

        return userDto;

    }
}
