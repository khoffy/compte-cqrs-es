package com.khoffylabs.comptecqrses.commands.aggregates;

import com.khoffylabs.comptecqrses.commonApi.commands.CreateAccountCommand;
import com.khoffylabs.comptecqrses.commonApi.enums.AccountStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

/**
 * L'aggregat definis l'etat de l'application
 */
@Aggregate
public class AccountAggregate {

    /**
     * L'identifiant de l'aggregat
     */
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    // Compulsory: a constructor without parameters inside an aggregate
    public AccountAggregate () {
        // Required by AXION
    }

    /**
     * Constructor with parameters
     * @param createAccountCommand
     */
    @CommandHandler
    public AccountAggregate (CreateAccountCommand createAccountCommand) {
        if(createAccountCommand.getInitialBalance() < 0) throw new RuntimeException("Initial balance negative!");
        // if OK, emit an event
        AggregateLifecycle.apply();
    }
}
