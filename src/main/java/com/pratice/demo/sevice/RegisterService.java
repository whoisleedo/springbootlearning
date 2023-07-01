package com.pratice.demo.sevice;

import com.pratice.demo.entity.Account;
import com.pratice.demo.entity.repository.AccountRepository;
import com.pratice.demo.exception.AccountUnavailableException;
import com.pratice.demo.vo.AccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {

    private final AccountRepository accountRepository;

    @Autowired
    public RegisterService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public long registerAccount(AccountVo accountVo) throws AccountUnavailableException {
        if(isAccountUnavailable(accountVo.getAccount())){
            throw new AccountUnavailableException("account_unavailable");
        }
        Account account = convertVoToEntity(accountVo);

        return accountRepository.save(account).getId();
    }

    public boolean isAccountUnavailable(String account){
        return  accountRepository.existsByUserAccount(account);
    }

    private Account convertVoToEntity(AccountVo accountVo){
        String account = accountVo.getAccount();
        String password = accountVo.getPassword();
        String name = accountVo.getName();
        String email = accountVo.getEmail();

        Account entity = new Account();
        entity.setUserAccount(account);
        //todo password need  crypt
        entity.setPassword(password);
        entity.setName(name);
        entity.setEmail(email);
        return entity;
    }
}
