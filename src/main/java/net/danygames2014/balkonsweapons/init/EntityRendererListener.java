package net.danygames2014.balkonsweapons.init;

import net.danygames2014.balkonsweapons.client.render.entity.BoomerangEntityRenderer;
import net.danygames2014.balkonsweapons.client.render.entity.SpearEntityRenderer;
import net.danygames2014.balkonsweapons.entity.projectile.BoomerangEntity;
import net.danygames2014.balkonsweapons.entity.projectile.SpearEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;

public class EntityRendererListener {
    @EventListener
    public void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(BoomerangEntity.class, new BoomerangEntityRenderer());
        event.renderers.put(SpearEntity.class, new SpearEntityRenderer());
    }
}
