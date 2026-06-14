package net.danygames2014.balkonsweapons.entity.projectile;

import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlowgunDartEntity extends MaterialProjectileEntity{
    public BlowgunDartEntity(World world) {
        super(world);
    }

    public BlowgunDartEntity(World world, double x, double y, double z) {
        this(world);
        setPickupStatus(PickupStatus.ALLOWED);
        setPosition(x, y, z);
    }

    public BlowgunDartEntity(World world, LivingEntity shooter, ItemStack itemStack) {
        this(world, shooter.x, shooter.y + shooter.getEyeHeight() - 0.1, shooter.z);
        setThrower(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemStack);
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
        if (entity.damage(getDamagingEntity(), (int)(1.0f + extraDamage))) {
            if (entity instanceof LivingEntity livingEntity) {
                // TODO: apply effect
//                for (PotionEffect pe : ItemBlowgunDart.getEffects(getWeapon())) {
//                    Potion type = Potion.potionTypes[pe.getPotionID()];
//                    if (type.isInstant()) {
//                        Entity shooter = getThrower();
//                        type.affectEntity(shooter instanceof EntityLivingBase ? (EntityLivingBase) shooter : null,
//                                living, pe.getAmplifier(), 1);
//                    } else {
//                        living.addPotionEffect(new PotionEffect(pe));
//                    }
//                }
            }
            applyEntityHitEffects(entity);
            playHitSound();
            markDead();
        } else {
            bounceBack();
        }
    }

    @Override
    public float getGravity() {
        return 0.05F;
    }

    @Override
    public void playHitSound() {
        SoundHelper.playSound(world, x, y, z, "random.bowhit", 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.2F));
    }

    @Override
    public int getMaxArrowShake() {
        return 4;
    }
}
