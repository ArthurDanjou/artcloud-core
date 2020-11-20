package fr.arthurdanjou.artcloud;

import com.github.dockerjava.api.DockerClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.arthurdanjou.artcloud.common.configuration.Configuration;
import fr.arthurdanjou.artcloud.common.docker.ArtDockerClient;
import fr.arthurdanjou.artcloud.common.logger.ArtLogger;
import fr.arthurdanjou.artcloud.common.managers.*;
import fr.arthurdanjou.artcloud.common.messaging.MessagingExchange;
import fr.arthurdanjou.artcloud.common.packets.log.LogPacket;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter
public abstract class ArtCloud {

    private boolean running;

    private final UUID uuid;
    protected Gson gson;
    private final List<AbstractManager> managers = new ArrayList<>();

    private final ArtLogger logger;

    private final FileManager fileManager;
    private final ConfigurationManager configurationManager;
    private final Configuration configuration;

    private final CommandsManager commandsManager;
    private final RabbitManager rabbitManager;
    private final RedisManager redisManager;

    private final DockerClient dockerClient;
    private final PacketManager packetManager;

    private final ExecutorService executorService;
    private final ScheduledExecutorService schedulerService;

    public ArtCloud(String name) {
        if (!System.getProperty("os.name").equals("Linux")) {
            System.exit(-1);
        }

        this.uuid = UUID.randomUUID();
        this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

        this.logger = new ArtLogger(this, name + " - " + uuid.toString());

        this.getLogger().space();
        this.getLogger().info("+---===[ A R T C L O U D ]===---+");
        this.getLogger().info("By : Arthur Danjou alias BunS");
        this.getLogger().info("Type : " + name);
        this.getLogger().info("Version : " + getVersion());
        this.getLogger().info("+---------------------------+");
        this.getLogger().space();

        this.executorService = Executors.newFixedThreadPool(8);
        this.schedulerService = Executors.newScheduledThreadPool(2);
        this.fileManager = new FileManager(this);
        this.configurationManager = new ConfigurationManager(this);
        this.configuration = this.configurationManager.getConfiguration();
        this.rabbitManager = new RabbitManager(this, this.configuration.getRabbitConfig());
        this.redisManager = new RedisManager(this, this.configuration.getRedisConfig());
        this.dockerClient = new ArtDockerClient(this).buildDockerClient();
        this.packetManager = new PacketManager(this, this.rabbitManager);
        this.commandsManager = new CommandsManager(this);

        this.registerListeners();
        this.registerCommands();

        this.enable();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.getLogger().info("Déconnexion en cours...");
            this.shutdown();
            this.getLogger().info("A bientôt !");
        }));

        running = true;
    }

    public abstract void enable();
    public abstract void disable();
    public abstract void registerListeners();
    public abstract void registerCommands();

    public void shutdown() {
        running = false;
        this.disable();
        this.managers.forEach(AbstractManager::disable);
        this.schedulerService.shutdown();
    }

    public String getVersion() {
        return ArtCloud.class.getPackage().getImplementationVersion();
    }
}
