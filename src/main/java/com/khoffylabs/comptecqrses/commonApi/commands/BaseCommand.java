package com.khoffylabs.comptecqrses.commonApi.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseCommand<T> {
    @TargetAggregateIdentifier  // L'id de la commande represente toujours l'identifiant de l'aggregat sur lequel on va appliquer la commande
    @Getter
    private T id;

    public BaseCommand(T id) {
        this.id = id;
    }
}
