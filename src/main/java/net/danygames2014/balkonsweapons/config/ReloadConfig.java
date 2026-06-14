package net.danygames2014.balkonsweapons.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;
import net.modificationstation.stationapi.api.util.Identifier;

public class ReloadConfig {

    @ConfigEntry(name = "Blowgun Reload Time", minValue = 0, maxValue = 1000, multiplayerSynced = true)
    public Integer blowgunReloadTime = 10;

    @ConfigEntry(name = "Crossbow Reload Time", minValue = 0, maxValue = 1000, multiplayerSynced = true)
    public Integer crossbowReloadTime = 15;

    @ConfigEntry(name = "Musket Reload Time", minValue = 0, maxValue = 1000, multiplayerSynced = true)
    public Integer musketReloadTime = 30;

    @ConfigEntry(name = "Blunderbuss Reload Time", minValue = 0, maxValue = 1000, multiplayerSynced = true)
    public Integer blunderbussReloadTime = 20;

    @ConfigEntry(name = "Flintlock Reload Time", minValue = 0, maxValue = 1000, multiplayerSynced = true)
    public Integer flintlockReloadTime = 15;

    @ConfigEntry(name = "Mortar Reload Time", minValue = 0, maxValue = 1000, multiplayerSynced = true)
    public Integer mortarReloadTime = 50;

    public int getReloadTime(Identifier reloadTimeTag) {
        switch (reloadTimeTag.getPath()) {
            case "blowgun": return blowgunReloadTime;
            case "crossbow": return crossbowReloadTime;
            case "musket": return musketReloadTime;
            case "blunderbuss": return blunderbussReloadTime;
            case "flintlock": return flintlockReloadTime;
            case "mortar": return mortarReloadTime;
            default: return 80;
        }
    }
}
