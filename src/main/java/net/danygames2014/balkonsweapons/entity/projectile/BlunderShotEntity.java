package net.danygames2014.balkonsweapons.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlunderShotEntity extends ProjectileEntity{
    public BlunderShotEntity(World world) {
        super(world);
    }

    public BlunderShotEntity(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
    }

    public BlunderShotEntity(World world, LivingEntity shooter) {
        this(world, shooter.x, shooter.y + shooter.getEyeHeight() - 0.1, shooter.z);
        setThrower(shooter);
    }

    @Override
    public void setAim(Entity entity, float f, float f1, float f2, float f3, float f4) {
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        setVelocity(x, y, z, f3, f4);
        velocityX += entity.velocityX;
        velocityZ += entity.velocityZ;
        if (!entity.onGround) {
            velocityY += entity.velocityY;
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (ticksInAir > 4) {
            markDead();
        }
    }

    @Override
    public void onEntityHit(HitResult hitResult) {
        Entity entity = hitResult.entity;
        float damage = 4.0f + extraDamage;
        // TODO: figure out hurtResistantTime
//        int prevhurtrestime = entity.hurtResistantTime;
        if (entity.damage(getDamagingEntity(), (int) damage)) {
//            entity.hurtResistantTime = prevhurtrestime;
            applyEntityHitEffects(entity);
            playHitSound();
            markDead();
        }
    }

    @Override
    public boolean aimRotation() {
        return false;
    }

    @Override
    public int getMaxLifetime() {
        return 200;
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }

    @Override
    public float getGravity() {
        return (getTotalVelocity() < 2.0) ? 0.04F : 0.0F;
    }

    public static void fireSpreadShot(World world, LivingEntity livingEntity, ItemStack itemstack) {
        for (int i = 0; i < 10; ++i) {
            BlunderShotEntity entity = new BlunderShotEntity(world, livingEntity);
            entity.setAim(livingEntity, livingEntity.pitch, livingEntity.yaw, 0.0f, 5.0f, 15.0f);
//            if (item != null && itemstack != null) {
//                RangedComponent.applyProjectileEnchantments(entity, itemstack);
//            }
            world.spawnEntity(entity);
        }
    }

    public static void fireFromDispenser(World world, double d, double d1, double d2, int i, int j, int k) {
        for (int i2 = 0; i2 < 10; ++i2) {
            BlunderShotEntity blunderShotEntity = new BlunderShotEntity(world, d, d1, d2);
            blunderShotEntity.setVelocity(i, j, k, 5.0f, 15.0f);
            world.spawnEntity(blunderShotEntity);
        }
    }
}
