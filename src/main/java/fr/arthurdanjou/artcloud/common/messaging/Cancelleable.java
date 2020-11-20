package fr.arthurdanjou.artcloud.common.messaging;

public interface Cancelleable {

    void setCancelled(boolean cancelled);
    boolean isCancelled();

}
