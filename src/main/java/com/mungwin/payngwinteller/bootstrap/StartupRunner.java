package com.mungwin.payngwinteller.bootstrap;


import com.mungwin.payngwinteller.constant.UtilityAccount;
import com.mungwin.payngwinteller.domain.model.account.Account;
import com.mungwin.payngwinteller.domain.model.account.AccountType;
import com.mungwin.payngwinteller.domain.repository.account.AccountRepository;
import com.mungwin.payngwinteller.security.service.AppSecurityContextHolder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StartupRunner implements CommandLineRunner {
    private final AccountRepository accountRepository;
    private final Map<String, Account> dict = new HashMap<>();

    public StartupRunner(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        bootstrapUtilityAccounts();
    }

    public void bootstrapUtilityAccounts() {
        List<Account> accountList = accountRepository.findAllByAccountType(AccountType.UTILITY);
        if (accountList != null && !accountList.isEmpty()) {
            for (Account acc: accountList) {
                dict.put(acc.getName(), acc);
            }
        }
        // collection
        Account collection = findByName(UtilityAccount.COLLECTION_NAME);
        // provider
        Account provider = findByName(UtilityAccount.PAYMENT_PROVIDER_NAME);
        // payngwin
        Account payngwin = findByName(UtilityAccount.PAYNGWIN_NAME);
        UtilityAccount.initialize(collection.getId(), provider.getId(), payngwin.getId());
    }
    public Account findByName(String name) {
        Account account = dict.get(name);
        if (account == null) {
            throw new RuntimeException(String.format("Account %s does not exist", name));
        }
        return account;
    }
}
