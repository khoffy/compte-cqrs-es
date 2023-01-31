package com.khoffylabs.comptecqrses.commonApi.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseCommand<T> {

    // L'id de la commande represente toujours l'identifiant de l'aggregat sur lequel on va appliquer la commande
    @TargetAggregateIdentifier
    @Getter
    private T id;

    // We need to provide a constructor as this abstract class is immutable (only getter, no setter)
    public BaseCommand(T id) {
        this.id = id;
    }
}
