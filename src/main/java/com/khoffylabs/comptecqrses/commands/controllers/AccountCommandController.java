package com.khoffylabs.comptecqrses.commands.controllers;

import com.khoffylabs.comptecqrses.commonApi.commands.CreateAccountCommand;
import com.khoffylabs.comptecqrses.commonApi.commands.CreditAccountCommand;
import com.khoffylabs.comptecqrses.commonApi.dtos.CreateAccountRequestDto;
import com.khoffylabs.comptecqrses.commonApi.dtos.CreditAccountRequestDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/commands/account")
public class AccountCommandController {

    /**
     *
     */
    private CommandGateway commandGateway;

    /**
     * Allows to read events from EventStore
     */
    private EventStore eventStore;
    @PostMapping(path = "/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDto request) {
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency())
        );
        return commandResponse;
    }

    @PutMapping(path = "/credit")
    public CompletableFuture<String> crediteAccount(@RequestBody CreditAccountRequestDto request) {
        CompletableFuture<String> commandResponse = commandGateway.send(new CreditAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency())
        );
        return commandResponse;
    }

    /**
     * Display all events related to an account
     * @param accountId
     * @return
     */
    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId) {
        return eventStore.readEvents(accountId).asStream();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        ResponseEntity<String> entity = new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return entity;
    }

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Autowired
    public void setEventStore(EventStore eventStore) {
        this.eventStore = eventStore;
    }
}
