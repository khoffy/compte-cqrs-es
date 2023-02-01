package com.khoffylabs.comptecqrses.queries.controllers;

import com.khoffylabs.comptecqrses.commonApi.queries.GetAccountByIdQuery;
import com.khoffylabs.comptecqrses.commonApi.queries.GetAllAccountsQuery;
import com.khoffylabs.comptecqrses.queries.entities.Account;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/query/accounts")
public class AccountQueryController {
    private QueryGateway queryGateway;

    @GetMapping("/allAccounts")
    public List<Account> accountList() {
        // To use QueryGateway, we need to create an object query
        // that will be created inside "queries" package in commonApi
        List<Account> response = queryGateway
                .query(new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class))
                .join();
        return response;
    }

    @GetMapping("/byId/{accountId}")
    public Account getAccount(@PathVariable String accountId) {
        // To use QueryGateway, we need to create an object query
        // that will be created inside "queries" package in commonApi
        Account response = queryGateway
                .query(new GetAccountByIdQuery(accountId), ResponseTypes.instanceOf(Account.class))
                .join();
        return response;
    }

    @Autowired
    public void setQueryGateway(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }
}
