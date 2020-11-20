package fr.arthurdanjou.artcloud.common.logger;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum LogLevel {

    INFO("INFO", LogColor.CYAN),
    SUCCESS("SUCCESS", LogColor.GREEN),
    DEBUG("DEBUG", LogColor.PURPLE),
    WARN("WARN", LogColor.YELLOW),
    ERROR("ERROR", LogColor.RED),
    FATAL("FATAL", LogColor.RED_BG);

    private final String name;
    private final LogColor color;

}
