package net.danygames2014.balkonsweapons.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class BlockDamageConfig {
    @ConfigEntry(name = "Dynamite block damage", multiplayerSynced = true)
    public Boolean dynamiteBlockDamage = true;

    @ConfigEntry(name = "Cannon block damage", multiplayerSynced = true)
    public Boolean cannonBlockDamage = true;

    @ConfigEntry(name = "Mortar block damage", multiplayerSynced = true)
    public Boolean mortarBlockDamage = true;


}
