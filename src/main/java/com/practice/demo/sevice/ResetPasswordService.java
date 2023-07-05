package com.practice.demo.sevice;

import com.practice.demo.dto.ResetPasswordDto;
import com.practice.demo.dto.common.StatusCode;
import com.practice.demo.entity.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class ResetPasswordService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public ResetPasswordService(AccountRepository accountRepository,
                                BCryptPasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public Optional<StatusCode> resetPassword(long id ,String account, ResetPasswordDto resetPasswordDto){
        return  accountRepository.findById(id)
                .filter(userDb -> userDb.getUserAccount().equals(account))
                .filter(userDb -> BCrypt.checkpw(resetPasswordDto.getCurrentPassword(), userDb.getPassword()))
                .map(userDb -> {
                    userDb.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
                    accountRepository.save(userDb);
                    return Optional.of(StatusCode.OK);
                }).orElseGet(() -> Optional.of(StatusCode.InvalidData));
    }
}
