package net.danygames2014.balkonsweapons.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class LegacyConfig {
    @ConfigEntry(name = "Legacy cannon model")
    public Boolean legacyCannon = false;

    @ConfigEntry(name = "Legacy boomerang model")
    public Boolean legacyBoomerang = false;

    @ConfigEntry(name = "Legacy javelin model")
    public Boolean legacyJavelin = false;

    @ConfigEntry(name = "Legacy knife model")
    public Boolean legacyKnife = false;

    @ConfigEntry(name = "Legacy spear model")
    public Boolean legacySpear = false;
}
