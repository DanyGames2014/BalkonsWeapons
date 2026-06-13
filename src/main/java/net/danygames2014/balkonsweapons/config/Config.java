package net.danygames2014.balkonsweapons.config;

import net.glasslauncher.mods.gcapi3.api.ConfigRoot;

public class Config {
    @ConfigRoot(value = "reload", visibleName = "Reload Config")
    public static final ReloadConfig RELOAD_CONFIG = new ReloadConfig();
}
