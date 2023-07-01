package com.pratice.demo.sevice;

import com.pratice.demo.entity.Account;
import com.pratice.demo.entity.repository.AccountRepository;
import com.pratice.demo.exception.AccountUnavailableException;
import com.pratice.demo.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(AccountRepository accountRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(rollbackFor = {AccountUnavailableException.class,Exception.class})
    public long registerAccount(AccountDto accountVo) throws AccountUnavailableException {
        if(isAccountUnavailable(accountVo.getAccount())){
            throw new AccountUnavailableException("account_unavailable");
        }
        Account account = convertVoToEntity(accountVo);

        return accountRepository.save(account).getId();
    }

    public boolean isAccountUnavailable(String account){
        return  accountRepository.existsByUserAccount(account);
    }

    private Account convertVoToEntity(AccountDto accountVo){
        String account = accountVo.getAccount();
        String password = accountVo.getPassword();
        String name = accountVo.getName();
        String email = accountVo.getEmail();

        Account entity = new Account();
        entity.setUserAccount(account);
        //todo password need  crypt
        entity.setPassword(passwordEncoder.encode(password));
        entity.setName(name);
        entity.setEmail(email);
        return entity;
    }
}
