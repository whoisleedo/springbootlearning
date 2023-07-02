package com.practice.demo.sevice;

import com.practice.demo.dto.UserDto;
import com.practice.demo.entity.Account;
import com.practice.demo.entity.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final AccountRepository accountRepository;
    @Autowired
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
