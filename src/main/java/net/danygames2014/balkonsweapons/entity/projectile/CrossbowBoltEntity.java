package net.danygames2014.balkonsweapons.entity.projectile;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class CrossbowBoltEntity extends ProjectileEntity {
    public CrossbowBoltEntity(World world) {
        super(world);
    }

    public CrossbowBoltEntity(World world, double x, double y, double z) {
        this(world);
        setPickupStatus(PickupStatus.ALLOWED);
        setPosition(x, y, z);
    }

    public CrossbowBoltEntity(World world, LivingEntity shooter) {
        this(world, shooter.x, shooter.y + shooter.getEyeHeight() - 0.1, shooter.z);
        setThrower(shooter);
        setPickupStatusFromEntity(shooter);
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
    public void onEntityHit(HitResult hitResult) {
        Entity entity = hitResult.entity;
        float vel = (float) getTotalVelocity();
        float damage = vel * 4.0f + extraDamage;
        if (entity.damage(getDamagingEntity(), (int)damage)) {
            if (entity instanceof LivingEntity && world.isRemote) {
//                ((EntityLivingBase) entity).setArrowCountInEntity(((EntityLivingBase) entity).getArrowCountInEntity() + 1);
            }
            applyEntityHitEffects(entity);
            playHitSound();
            markDead();
        } else {
            bounceBack();
        }
    }

    @Override
    public void playHitSound() {
        SoundHelper.playSound(world, x, y, z, "random.bowhit", 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.4F));
    }

    @Override
    public int getMaxArrowShake() {
        return 4;
    }

    @NotNull
    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeapons.crossbowBolt, 1);
    }
}
