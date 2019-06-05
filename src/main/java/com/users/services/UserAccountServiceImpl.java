package com.users.services;

import com.users.entities.UserAccount;
import com.users.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public Set<UserAccount> findAll() {
        Set<UserAccount> userAccountSet = new HashSet<>();
        userAccountRepository.findAll().forEach(userAccountSet::add);
        return userAccountSet;
    }

    @Override
    public UserAccount findById(Long aLong) {
        return userAccountRepository.findById(aLong).orElse(null);
    }

    @Override
    public UserAccount save(UserAccount object) {
        return userAccountRepository.save(object);
    }

    @Override
    public void deleteById(Long aLong) {
        userAccountRepository.deleteById(aLong);
    }

    @Override
    public UserAccount findUserAccountByLogin(String login) {
        return userAccountRepository.findUserAccountByLogin(login);
    }
}
