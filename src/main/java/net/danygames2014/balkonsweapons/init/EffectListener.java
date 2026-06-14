package net.danygames2014.balkonsweapons.init;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.balkonsweapons.effect.SlownessEffect;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.effect.EntityEffectTypeRegistryEvent;

public class EffectListener {
    @EventListener
    public void registerEffects(EntityEffectTypeRegistryEvent event) {
        event.register(BalkonsWeapons.NAMESPACE.id("slowness"), SlownessEffect.TYPE);
    }
}
