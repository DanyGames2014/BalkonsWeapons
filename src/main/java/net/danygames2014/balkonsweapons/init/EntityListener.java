package net.danygames2014.balkonsweapons.init;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.balkonsweapons.entity.projectile.BoomerangEntity;
import net.danygames2014.balkonsweapons.entity.projectile.SpearEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.entity.EntityRegisterEvent;

public class EntityListener {
    @EventListener
    public void registerEntities(EntityRegisterEvent event) {
        event.register(BalkonsWeapons.NAMESPACE.id("boomerang"), BoomerangEntity.class);
        event.register(BalkonsWeapons.NAMESPACE.id("spear"), SpearEntity.class);
    }
}
