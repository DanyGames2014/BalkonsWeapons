package net.danygames2014.balkonsweapons.config;

import net.glasslauncher.mods.gcapi3.api.ConfigRoot;

public class Config {
    @ConfigRoot(value = "reload", visibleName = "Reload Config")
    public static final ReloadConfig RELOAD_CONFIG = new ReloadConfig();

    @ConfigRoot(value = "legacy", visibleName = "Legacy Config")
    public static final LegacyConfig LEGACY_CONFIG = new LegacyConfig();

    @ConfigRoot(value = "block_damage", visibleName = "Block Damage Config")
    public static final BlockDamageConfig BLOCK_DAMAGE_CONFIG = new BlockDamageConfig();
}
