package net.danygames2014.balkonsweapons.item;

public enum ReloadState {
    STATE_NONE,
    STATE_RELOADED,
    STATE_READY;

    public boolean isReloaded() {
        return this == STATE_RELOADED || this == STATE_READY;
    }
}
