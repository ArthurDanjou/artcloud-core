package fr.arthurdanjou.artcloud.common.managers;

import fr.arthurdanjou.artcloud.ArtCloud;

public abstract class AbstractManager {

    protected final ArtCloud artCloud;

    public AbstractManager(ArtCloud artCloud, String managerName) {
        this.artCloud = artCloud;
        this.artCloud.getManagers().add(this);
        this.enable();
        this.artCloud.getLogger().info(managerName+ "Manager succesfully loaded !");
    }

    public abstract void enable();
    public abstract void disable();

}
