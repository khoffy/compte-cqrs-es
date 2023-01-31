package com.khoffylabs.comptecqrses.commonApi.commands;

import lombok.Getter;

/**
 * Command for Account creation
 */
public class CreateAccountCommand extends BaseCommand<String> {
    @Getter
    private double initialBalance;

    @Getter
    private String currency;
    public CreateAccountCommand(String id, double initialBalance, String currency) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
    }
}
