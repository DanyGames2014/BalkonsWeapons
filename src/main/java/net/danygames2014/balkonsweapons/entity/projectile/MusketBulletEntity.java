package net.danygames2014.balkonsweapons.entity.projectile;

import net.danygames2014.nyalib.particle.ParticleHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MusketBulletEntity extends ProjectileEntity {
    public MusketBulletEntity(World world) {
        super(world);
        setPickupStatus(PickupStatus.DISALLOWED);
    }

    public MusketBulletEntity(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
    }

    public MusketBulletEntity(World world, LivingEntity shooter) {
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
        if (inGround) {
            if (random.nextInt(4) == 0) {
                ParticleHelper.addParticle(world, "smoke", x, y, z, 0.0, 0.0, 0.0);
            }
            return;
        }
        double speed = getTotalVelocity();
        double amount = 16.0;
        if (speed > 2.0) {
            for (int i1 = 1; i1 < amount; ++i1) {
                ParticleHelper.addParticle(world, "explode", x + velocityX * i1 / amount,
                        y + velocityY * i1 / amount, z + velocityZ * i1 / amount, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void onEntityHit(HitResult hitResult) {
        Entity entity = hitResult.entity;
        float damage = 20.0f + extraDamage;
        if (entity.damage(getDamagingEntity(), (int)damage)) {
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
    public float getAirResistance() {
        return 0.98F;
    }

    @Override
    public float getGravity() {
        return (getTotalVelocity() < 3.0) ? 0.07F : 0.0F;
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }
}
