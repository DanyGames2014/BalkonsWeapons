package net.danygames2014.balkonsweapons.init;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.balkonsweapons.entity.CannonEntity;
import net.danygames2014.balkonsweapons.entity.DummyEntity;
import net.danygames2014.balkonsweapons.entity.projectile.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.entity.EntityRegisterEvent;

public class EntityListener {
    @EventListener
    public void registerEntities(EntityRegisterEvent event) {
        event.register(BalkonsWeapons.NAMESPACE.id("boomerang"), BoomerangEntity.class);
        event.register(BalkonsWeapons.NAMESPACE.id("spear"), SpearEntity.class);
        event.register(BalkonsWeapons.NAMESPACE.id("dynamite"), DynamiteEntity.class);
        event.register(BalkonsWeapons.NAMESPACE.id("javelin"), JavelinEntity.class);
        event.register(BalkonsWeapons.NAMESPACE.id("knife"), KnifeEntity.class);
        event.register(BalkonsWeapons.NAMESPACE.id("cannon_ball"), CannonBallEntity.class);
        event.register(BalkonsWeapons.NAMESPACE.id("cannon"), CannonEntity.class);
        event.register(BalkonsWeapons.NAMESPACE.id("dummy"), DummyEntity.class);
        event.register(BalkonsWeapons.NAMESPACE.id("blunder_shot"), BlunderShotEntity.class);
        event.register(BalkonsWeapons.NAMESPACE.id("mortar_shell"), MortarShellEntity.class);
    }
}
