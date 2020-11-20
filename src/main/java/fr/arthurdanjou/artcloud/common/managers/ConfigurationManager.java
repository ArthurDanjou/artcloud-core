package fr.arthurdanjou.artcloud.common.managers;

import fr.arthurdanjou.artcloud.ArtCloud;
import fr.arthurdanjou.artcloud.common.configuration.Configuration;
import lombok.Getter;

@Getter
public class ConfigurationManager extends AbstractManager {

    private Configuration configuration;

    public ConfigurationManager(ArtCloud artCloud) {
        super(artCloud, "Configuration");
    }

    public boolean isConfigurationLoaded() {
        return this.configuration != null;
    }

    @Override
    public void enable() {
        String config = this.artCloud.getFileManager().loadFile(this.artCloud.getFileManager().getConfigFile());
        if ("".equals(config)) {
            configuration = new Configuration();
            String content = this.artCloud.getGson().toJson(this.configuration);
            this.artCloud.getFileManager().saveFile(this.artCloud.getFileManager().getConfigFile(), content);
            this.artCloud.getLogger().warn("Veuillez remplir le fichier de configuration, puis redémarrer moi !");
            System.exit(1);
        } else {
            configuration = this.artCloud.getGson().fromJson(config, Configuration.class);
            this.artCloud.getLogger().info("Lecture effectuée du fichier de configuration !");
        }
    }

    @Override
    public void disable() {

    }
}
