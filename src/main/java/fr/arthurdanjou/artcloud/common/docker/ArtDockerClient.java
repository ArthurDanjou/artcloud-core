package fr.arthurdanjou.artcloud.common.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import fr.arthurdanjou.artcloud.ArtCloud;
import fr.arthurdanjou.artcloud.common.configuration.needed.DockerConfig;

public class ArtDockerClient {

    private final ArtCloud artCloud;
    private final DockerConfig dockerConfig;

    public ArtDockerClient(ArtCloud artCloud) {
        this.artCloud = artCloud;
        this.dockerConfig = this.artCloud.getConfiguration().getDockerConfig();
    }

    public DockerClient buildDockerClient() {
        DockerClientConfig custom = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://" + this.dockerConfig.getHost() + ":" + this.dockerConfig.getPort())
                .withDockerTlsVerify(true)
                .withDockerCertPath("/home/" + this.dockerConfig.getFileUser() + "/.docker")
                .withRegistryUsername(this.dockerConfig.getUsername())
                .withRegistryPassword(this.dockerConfig.getPassword())
                .withRegistryEmail(this.dockerConfig.getEmail())
                .withRegistryUrl(this.dockerConfig.getUrl())
                .build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(custom.getDockerHost())
                .sslConfig(custom.getSSLConfig())
                .build();

        this.artCloud.getLogger().info("DockerClient construit avec succès !");
        return DockerClientImpl.getInstance(custom, httpClient);
    }

}
