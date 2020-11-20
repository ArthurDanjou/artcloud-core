package fr.arthurdanjou.artcloud.common.managers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import fr.arthurdanjou.artcloud.ArtCloud;
import fr.arthurdanjou.artcloud.common.configuration.needed.RabbitConfig;
import lombok.Getter;

@Getter
public class RabbitManager extends AbstractManager{

    private final RabbitConfig rabbitConfig;
    private ConnectionFactory connectionFactory;
    private Channel channel;
    private Connection connection;

    public RabbitManager(ArtCloud artCloud, RabbitConfig rabbitConfig) {
        super(artCloud, "Rabbit");
        this.rabbitConfig = rabbitConfig;
    }

    @Override
    public void enable() {
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setHost(rabbitConfig.getHost());
        this.connectionFactory.setPort(rabbitConfig.getPort());
        this.connectionFactory.setUsername(rabbitConfig.getUsername());
        this.connectionFactory.setPassword(rabbitConfig.getPassword());
        connect();
    }

    @Override
    public void disable() {
        try {
            this.artCloud.getLogger().info("Déconnection à RabbitMQ !");
            channel.close();
            connection.close();
        } catch (Exception e) {
            this.artCloud.getLogger().error("Impossible de se déconnecter à RabbitMQ !");
            e.printStackTrace();
        }
    }

    public void connect(){
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            this.artCloud.getLogger().info("Connecté à RabbitMQ !");
        } catch (Exception e){
            this.artCloud.getLogger().error("Impossible de se connecter à RabbitMQ !");
            e.printStackTrace();
        }
    }

    public Channel getChannel() {
        return channel;
    }
}
