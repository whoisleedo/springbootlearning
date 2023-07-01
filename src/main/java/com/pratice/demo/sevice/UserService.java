package com.pratice.demo.sevice;

import com.pratice.demo.dto.AccountDto;
import com.pratice.demo.dto.UserDto;
import com.pratice.demo.entity.Account;
import com.pratice.demo.entity.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final AccountRepository accountRepository;

    public UserService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<UserDto> getUserById(long id){
        return accountRepository.findById(id).map(this::convertUserVo);
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
