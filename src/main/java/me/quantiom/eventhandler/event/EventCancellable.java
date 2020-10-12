package me.quantiom.eventhandler.event;

public interface EventCancellable {
    boolean isCancelled();
    void setCancelled(boolean cancelled);
}
