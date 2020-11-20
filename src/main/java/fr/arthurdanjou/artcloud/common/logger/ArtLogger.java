package fr.arthurdanjou.artcloud.common.logger;

import fr.arthurdanjou.artcloud.ArtCloud;
import fr.arthurdanjou.artcloud.common.messaging.MessagingExchange;
import fr.arthurdanjou.artcloud.common.packets.log.LogPacket;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ArtLogger {

    private final ArtCloud artCloud;
    private final String name;

    public ArtLogger(ArtCloud artCloud, String name) {
        this.artCloud = artCloud;
        this.name = name;
    }

    private void log(LogLevel level, String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss");
        System.out.println(
                LogColor.RESET.getColor() + String.format("[%1$s] - %2$s - %3$s",
                                dateFormat.format(new Date()),
                                level.getColor().getColor() + level.getName() + LogColor.RESET.getColor(),
                                message
                        )
        );
        this.artCloud.getPacketManager().publishMessage(MessagingExchange.LOGS, new LogPacket(this.name, message));
    }

    public void warn(String message) {
        this.log(LogLevel.WARN, message);
    }

    public void success(String message) {
        this.log(LogLevel.SUCCESS, message);
    }

    public void fatal(String message) {
        this.log(LogLevel.FATAL, message);
    }

    public void error(String message) {
        this.log(LogLevel.ERROR, message);
    }

    public void info(String message) {
        this.log(LogLevel.INFO, message);
    }

    public void debug(String message) {
        this.log(LogLevel.DEBUG, message);
    }

    public void space() {
        this.log(LogLevel.INFO, " ");
    }

}
