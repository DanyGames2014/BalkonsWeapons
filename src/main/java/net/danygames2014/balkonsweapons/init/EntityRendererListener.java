package net.danygames2014.balkonsweapons.init;

import net.danygames2014.balkonsweapons.client.render.entity.*;
import net.danygames2014.balkonsweapons.entity.CannonEntity;
import net.danygames2014.balkonsweapons.entity.DummyEntity;
import net.danygames2014.balkonsweapons.entity.projectile.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;

public class EntityRendererListener {
    @EventListener
    public void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(BoomerangEntity.class, new BoomerangEntityRenderer());
        event.renderers.put(SpearEntity.class, new SpearEntityRenderer());
        event.renderers.put(DynamiteEntity.class, new DynamiteEntityRenderer());
        event.renderers.put(JavelinEntity.class, new JavelinEntityRenderer());
        event.renderers.put(KnifeEntity.class, new KnifeEntityRenderer());
        event.renderers.put(CannonBallEntity.class, new CannonBallEntityRenderer());
        event.renderers.put(CannonEntity.class, new CannonEntityRenderer());
        event.renderers.put(DummyEntity.class, new DummyEntityRenderer());
        event.renderers.put(BlunderShotEntity.class, new BlunderShotEntityRenderer());
        event.renderers.put(MortarShellEntity.class, new MortarShellEntityRenderer());
        event.renderers.put(MusketBulletEntity.class, new MusketBulletEntityRenderer());
        event.renderers.put(CrossbowBoltEntity.class, new CrossbowBoltEntityRenderer());
    }
}
