package net.danygames2014.balkonsweapons.entity.projectile;

import net.danygames2014.balkonsweapons.item.component.WeaponItem;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SpearEntity extends MaterialProjectileEntity{
    public SpearEntity(World world) {
        super(world);
    }

    public SpearEntity(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
    }

    public SpearEntity(World world, LivingEntity shooter, ItemStack itemStack) {
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
        if (world.isRemote) {
            return;
        }

        ItemStack thrownItem = getWeapon();
        if (thrownItem == null) return;
        Item item = thrownItem.getItem();
        Entity entity = hitResult.entity;
        if (item instanceof WeaponItem weaponItem && entity.damage(owner, (int)weaponItem.getEntityDamage())) {
            applyEntityHitEffects(entity);
            playHitSound();
            if (thrownItem.getDamage() + 1 >= thrownItem.getMaxDamage()) {
                thrownItem.split(1);
                if (thrownItem.count <= 0) {
                    setThrownItemStack(null);
                }
                markDead();
            } else {
                thrownItem.damage(1, owner);
                if (thrownItem.count <= 0) {
                    setThrownItemStack(null);
                }
                setVelocityClient(0.0, 0.0, 0.0);
            }
        } else {
            bounceBack();
        }
    }

    @Override
    public void playHitSound() {
        SoundHelper.playSound(world, x, y, z, "random.bowhit", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.9F));
    }

    @Override
    public int getMaxLifetime() {
        return (pickupStatus == PickupStatus.ALLOWED || pickupStatus == PickupStatus.OWNER_ONLY) ? 0 : 1200;
    }

    @Override
    public int getMaxArrowShake() {
        return 10;
    }
}
