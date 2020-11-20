package fr.arthurdanjou.artcloud.common.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum MessagingExchange {

    LOGS("logs"),
    WEB("web"),
    MASTER("master"),
    CLIENTS("clients"),
    MONITORING("monitoring"),
    DISCORD("discord"),
    ;

    private final String name;

}
