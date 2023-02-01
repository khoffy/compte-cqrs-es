package com.khoffylabs.comptecqrses.commands.aggregates;

import com.khoffylabs.comptecqrses.commonApi.commands.CreateAccountCommand;
import com.khoffylabs.comptecqrses.commonApi.enums.AccountStatus;
import com.khoffylabs.comptecqrses.commonApi.events.AccountActivatedEvent;
import com.khoffylabs.comptecqrses.commonApi.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

/**
 * L'aggregate definis l'etat de l'application
 */
@Aggregate
public class AccountAggregate {

    /**
     * L'identifiant de l'aggregate
     */
    @AggregateIdentifier
    private String accountId; // L'id results from the command and is automatically assigned via this annotation
    private double balance;
    private String currency;
    private AccountStatus status;

    // Compulsory: a constructor without parameters inside an aggregate
    public AccountAggregate () {
        // Required by AXION
    }

    /**
     * Constructor with parameters
     * As soon as the AccountCreatedEvent is emitted, it is persisted inside the Event Store (domain event store)
     * Decision function
     * @param createAccountCommand
     */
    @CommandHandler
    public AccountAggregate (CreateAccountCommand createAccountCommand) {
        if(createAccountCommand.getInitialBalance() < 0) throw new RuntimeException("Initial balance negative!");
        // if OK, emit an event
        AggregateLifecycle.apply(
                new AccountCreatedEvent(createAccountCommand.getId(),
                        createAccountCommand.getInitialBalance(),
                        createAccountCommand.getCurrency()
                )
        );
    }

    /**
     * As soon as the event AccountCreatedEvent is emitted by the "Decision function",
     * this function is going to execute
     * Evolution function
     * @param accountCreatedEvent
     */
    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        this.accountId = accountCreatedEvent.getId();
        this.balance = accountCreatedEvent.getInitialBalance();
        this.currency = accountCreatedEvent.getCurrency();
        this.status = AccountStatus.CREATED;

        // Once the aggregate state has changed, one can emit another event
        AggregateLifecycle.apply(
                new AccountActivatedEvent(
                        accountCreatedEvent.getId(),
                        AccountStatus.ACTIVATED
                ));
    }

    // Emitting another event inside the AccountCreatedEvent handler, we need to mutate the app state
    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent) {
        this.status = accountActivatedEvent.getStatus();
    }
}
