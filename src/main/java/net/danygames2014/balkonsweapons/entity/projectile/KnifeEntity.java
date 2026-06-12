package net.danygames2014.balkonsweapons.entity.projectile;

import net.danygames2014.balkonsweapons.item.component.WeaponItem;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class KnifeEntity extends MaterialProjectileEntity{

    private int soundTimer;

    public KnifeEntity(World world) {
        super(world);
    }

    public KnifeEntity(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
    }

    public KnifeEntity(World world, LivingEntity shooter, ItemStack stack) {
        this(world, shooter.x, shooter.y + shooter.getEyeHeight() - 0.1, shooter.z);
        setThrower(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(stack);
        soundTimer = 0;
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
        if (inGround || beenInGround) {
            return;
        }
        pitch -= 70.0f;
        if (pitch <= -360) pitch += 360;
        if (soundTimer >= 3) {
            if (!isInFluid(Material.WATER)) {
                SoundHelper.playSound(world, x, y, z, "random.bow", 0.6F, 1.0F / (random.nextFloat() * 0.2F + 0.6F + ticksInAir / 15F));
            }
            soundTimer = 0;
        }
        ++soundTimer;
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
        if (item instanceof WeaponItem weaponItem && entity.damage(getDamagingEntity(), (int) weaponItem.getEntityDamage())) {
            applyEntityHitEffects(entity);
            if (thrownItem.getDamage() + 2 >= thrownItem.getMaxDamage()) {
                thrownItem.split(1);
                if (thrownItem.count <= 0) {
                    setThrownItemStack(null);
                }
                markDead();
            } else {
                thrownItem.damage(2, getDamagingEntity());
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
    public boolean aimRotation() {
        return beenInGround;
    }

    @Override
    public int getMaxLifetime() {
        return (pickupStatus == PickupStatus.ALLOWED || pickupStatus == PickupStatus.OWNER_ONLY) ? 0 : 1200;
    }

    @Override
    public int getMaxArrowShake() {
        return 4;
    }

    @Override
    public float getGravity() {
        return 0.03F;
    }

    @Override
    public float getAirResistance() {
        return 0.98F;
    }
}
