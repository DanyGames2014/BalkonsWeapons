package net.danygames2014.balkonsweapons.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class ThrowConfig {
    @ConfigEntry(name = "Can throw spear")
    public Boolean throwSpear = true;

    @ConfigEntry(name = "Can throw knife")
    public Boolean throwKnife = true;
}
