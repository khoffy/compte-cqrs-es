package com.khoffylabs.comptecqrses.queries.services;

import com.khoffylabs.comptecqrses.commonApi.enums.OperationType;
import com.khoffylabs.comptecqrses.commonApi.events.AccountActivatedEvent;
import com.khoffylabs.comptecqrses.commonApi.events.AccountCreatedEvent;
import com.khoffylabs.comptecqrses.commonApi.events.AccountCreditedEvent;
import com.khoffylabs.comptecqrses.commonApi.events.AccountDebitedEvent;
import com.khoffylabs.comptecqrses.queries.entities.Account;
import com.khoffylabs.comptecqrses.queries.entities.Operation;
import com.khoffylabs.comptecqrses.queries.repositories.AccountRepository;
import com.khoffylabs.comptecqrses.queries.repositories.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@Slf4j
public class AccountServiceHandler {

    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.info("**********************************");
        log.info("AccountCreatedEvent received in AccountServiceHandler");
        Account account = new Account();
        account.setId(event.getId());
        account.setBalance(event.getInitialBalance());
        account.setCurrency(event.getCurrency());
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent event) {
        log.info("**********************************");
        log.info("AccountActivatedEvent received in AccountServiceHandler");
        Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event) {
        log.info("**********************************");
        log.info("AccountDebitedEvent received in AccountServiceHandler");
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setType(OperationType.DEBIT);
        operation.setAmount(event.getAmount());
        //Not to do: operation date has to be set during the command instantiation
        operation.setCreateAt(LocalDateTime.now());
        operationRepository.save(operation);
        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event) {
        log.info("**********************************");
        log.info("AccountDebitedEvent received in AccountServiceHandler");
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setType(OperationType.CREDIT);
        operation.setAmount(event.getAmount());
        //Not to do: operation date has to be set during the command instantiation
        operation.setCreateAt(LocalDateTime.now());
        operationRepository.save(operation);
        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setOperationRepository(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }
}
