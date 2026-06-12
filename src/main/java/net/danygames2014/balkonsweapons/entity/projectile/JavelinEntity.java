package net.danygames2014.balkonsweapons.entity.projectile;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class JavelinEntity extends ProjectileEntity {
    public JavelinEntity(World world) {
        super(world);
    }

    public JavelinEntity(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
    }

    public JavelinEntity(World world, LivingEntity shooter) {
        this(world, shooter.x, shooter.y + shooter.getEyeHeight() - 0.1, shooter.z);
        setThrower(shooter);
        setPickupStatusFromEntity(shooter);
    }

    @Override
    public void setAim(Entity entity, float f, float f1, float f2, float f3, float f4) {
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        setVelocity(x, y, z, f3 * 1.1f, f4);
        velocityX += entity.velocityX;
        velocityZ += entity.velocityZ;
        if (!entity.onGround) {
            velocityY += entity.velocityY;
        }
    }

    @Override
    public void onEntityHit(HitResult hitResult) {
        double vel = getTotalVelocity();
        int damage = (int)Math.ceil(vel * (3.0 + extraDamage));
        if (getIsCritical()) {
            damage += random.nextInt(damage / 2 + 2);
        }
        Entity entity = hitResult.entity;
        if (entity.damage(getDamagingEntity(), damage)) {
            applyEntityHitEffects(entity);
            playHitSound();
            markDead();
        } else {
            bounceBack();
        }
    }

    @Override
    public void playHitSound() {
        SoundHelper.playSound(world, x, y, z, "random.bowhit", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.9F));
    }

    @Override
    public boolean canBeCritical() {
        return true;
    }

    @Override
    public int getMaxArrowShake() {
        return 10;
    }

    @Override
    public float getGravity() {
        return 0.03F;
    }

    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeapons.javelin);
    }
}
