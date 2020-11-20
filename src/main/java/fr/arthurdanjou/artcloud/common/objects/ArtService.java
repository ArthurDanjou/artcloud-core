package fr.arthurdanjou.artcloud.common.objects;

import com.github.dockerjava.api.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter @AllArgsConstructor
public class ArtService implements Serializable {

    private final String name;

    private final String imageId;
    private final String network;
    private final String hostname;

    private final int replicaCount;
    private final boolean isPersistent;

    private final int maxMemory;

    private final int publishedPort;
    private final int targetPort;


    public ServiceSpec toServiceSpec(boolean restart) {
        List<Mount> mounts = new ArrayList<>();

        if (isPersistent) {
            Mount mount = new Mount()
                    .withSource(this.name + "-data")
                    .withVolumeOptions(new VolumeOptions());
            mounts.add(mount);
        }

        ContainerSpec containerSpec = new ContainerSpec()
                .withImage(this.imageId)
                .withHostname(this.hostname)
                .withMounts(mounts);

        List<NetworkAttachmentConfig> networks = new ArrayList<>();
        networks.add(
                new NetworkAttachmentConfig()
                        .withTarget(this.network)
        );

        EndpointSpec endpointSpec = new EndpointSpec()
                .withMode(EndpointResolutionMode.VIP)
                .withPorts(Collections.singletonList(
                        new PortConfig()
                                .withProtocol(PortConfigProtocol.TCP)
                                .withPublishedPort(this.publishedPort)
                                .withTargetPort(this.targetPort)
                ));

        ResourceRequirements resourceRequirements = new ResourceRequirements()
                .withLimits(new ResourceSpecs().withMemoryBytes(maxMemory));

        TaskSpec taskSpec = new TaskSpec()
                .withContainerSpec(containerSpec)
                .withNetworks(networks)
                .withResources(resourceRequirements);
        if (restart)
            taskSpec.withForceUpdate(1);

        ServiceModeConfig serviceModeConfig = new ServiceModeConfig()
                .withReplicated(new ServiceReplicatedModeOptions().withReplicas(this.replicaCount));

        return new ServiceSpec()
                .withName(this.name)
                .withTaskTemplate(taskSpec)
                .withEndpointSpec(endpointSpec)
                .withMode(serviceModeConfig);
    }

}
