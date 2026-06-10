package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.entity.projectile.MaterialProjectileEntity;
import net.minecraft.util.hit.HitResult;

public interface ItemHitEffect {
    void onEntityHit(MaterialProjectileEntity entity, HitResult hitResult);

    void onBlockHit(MaterialProjectileEntity entity, HitResult hitResult);
}
