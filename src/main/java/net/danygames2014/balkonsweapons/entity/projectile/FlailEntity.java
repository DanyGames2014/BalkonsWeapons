package net.danygames2014.balkonsweapons.entity.projectile;

import net.danygames2014.balkonsweapons.item.FlailItem;
import net.danygames2014.balkonsweapons.util.EntityUtil;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FlailEntity extends MaterialProjectileEntity{
    public boolean isSwinging;
    private float flailDamage;
    private double distanceTotal;
    private double distanceX;
    private double distanceY;
    private double distanceZ;

    public FlailEntity(World world) {
        super(world);
    }

    public FlailEntity(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
    }

    public FlailEntity(World world, LivingEntity shooter, ItemStack itemStack) {
        this(world, shooter.x, shooter.y + shooter.getEyeHeight() - 0.1, shooter.z);
        setThrower(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemStack);
        distanceTotal = 0;
    }

    @Override
    public void setAim(Entity entity, float f, float f1, float f2, float f3, float f4) {
        velocityX += entity.velocityX;
        velocityZ += entity.velocityZ;
        if (!entity.onGround) {
            velocityY += entity.velocityY;
        }
        swing(f, f1, f3, f4);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        Entity shooter = getThrower();
        if (shooter != null) {
            distanceX = shooter.x - x;
            distanceY = shooter.y - y;
            distanceZ = shooter.z - z;
            distanceTotal =
                    Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
            if (distanceTotal > 3.0) {
                returnToOwner(true);
            }
            if (shooter instanceof LivingEntity livingEntity) {
                ItemStack itemstack = livingEntity.getHeldItem();
                ItemStack thrownItem = getWeapon();
                if (itemstack == null || (thrownItem != null && !ItemStack.areEqual(itemstack, thrownItem)) || !shooter.isAlive()) {
                    pickUpByOwner();
                }
            }
        } else if (!world.isRemote) {
            markDead();
        }
        if (inGround) {
            inGround = false;
            return;
        }
        returnToOwner(false);
    }

    public void returnToOwner(boolean looseFromGround) {
        if (looseFromGround) {
            inGround = false;
        }
        Entity shooter = getThrower();
        if (shooter == null) {
            return;
        }
        double targetPosX = shooter.x;
        double targetPosY = EntityUtil.getBoundingBox(shooter).minY + 0.4000000059604645;
        double targetPosZ = shooter.z;
        float f = 27.0f;
        float f2 = 2.0f;
        targetPosX += -Math.sin((shooter.yaw + f) * 0.017453292f) * Math.cos(shooter.pitch * 0.017453292f) * f2;
        targetPosZ += Math.cos((shooter.yaw + f) * 0.017453292f) * Math.cos(shooter.pitch * 0.017453292f) * f2;
        distanceX = targetPosX - x;
        distanceY = targetPosY - y;
        distanceZ = targetPosZ - z;
        distanceTotal =
                Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
        if (distanceTotal > 3.0) {
            x = targetPosX;
            y = targetPosY;
            z = targetPosZ;
        } else if (distanceTotal > 2.5) {
            isSwinging = false;
            velocityX *= -0.5;
            velocityY *= -0.5;
            velocityZ *= -0.5;
        }
        if (!isSwinging) {
            float f3 = 0.2f;
            velocityX = distanceX * f3 * distanceTotal;
            velocityY = distanceY * f3 * distanceTotal;
            velocityZ = distanceZ * f3 * distanceTotal;
        }
    }


    // TODO: pick this up later when a better solution is found
    public void pickUpByOwner() {
        markDead();
        Entity shooter = getThrower();
        if (shooter instanceof PlayerEntity player && getWeapon() != null) {
//            PlayerWeaponData.setFlailThrown((EntityPlayer) shooter, false);
        }
    }

    public void swing(float f, float f1, float f2, float f3) {
        if (isSwinging) {
            return;
        }
        SoundHelper.playSound(world, owner.x, owner.y, owner.z, "random.bow", 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        setVelocity(x, y, z, f2, f3);
        isSwinging = true;
        inGround = false;
    }

    @Override
    public void onEntityHit(HitResult hitResult) {
        Entity entity = hitResult.entity;
        if (entity == owner) {
            return;
        }
        Entity shooter = getDamagingEntity();
        if (entity.damage(getDamagingEntity(), (int)flailDamage)) {
            applyEntityHitEffects(entity);
            playHitSound();
            returnToOwner(true);
        } else {
            bounceBack();
        }
    }

    @Override
    protected void bounceBack() {
        velocityX *= -0.8;
        velocityY *= -0.8;
        velocityZ *= -0.8;
        yaw += 180.0f;
        prevYaw += 180.0f;
        ticksInAir = 0;
    }

    @Override
    public void playHitSound() {
        if (inGround) {
            return;
        }
        SoundHelper.playSound(world, x, y, z, "random.hurt", 1.0F, random.nextFloat() * 0.4F + 0.8F);
    }

    @Override
    public void setThrownItemStack(@Nullable ItemStack itemstack) {
        if (itemstack != null && !(itemstack.getItem() instanceof FlailItem)) {
            return;
        }
        super.setThrownItemStack(itemstack);
        flailDamage = itemstack == null ? 0 : ((FlailItem) itemstack.getItem()).getFlailDamage();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putFloat("fDmg", flailDamage);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        flailDamage = nbt.getFloat("fDmg");
    }

    @Override
    public void onPlayerInteraction(PlayerEntity player) {
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }
}
