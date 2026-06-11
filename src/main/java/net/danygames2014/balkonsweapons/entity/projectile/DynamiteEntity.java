package net.danygames2014.balkonsweapons.entity.projectile;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.balkonsweapons.util.PhysHelper;
import net.danygames2014.nyalib.particle.ParticleHelper;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.States;

public class DynamiteEntity extends ProjectileEntity{

    private int explodefuse;
    private boolean extinguished;

    public DynamiteEntity(World world) {
        super(world);
    }

    public DynamiteEntity(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
    }

    public DynamiteEntity(World world, LivingEntity shooter, int i) {
        this(world, shooter.x, shooter.y + shooter.getEyeHeight() - 0.1, shooter.z);
        setThrower(shooter);
        explodefuse = i;
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
    protected void initDataTracker() {
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (!inGround && !beenInGround) {
            pitch -= 50.0f;
            if (pitch <= -360) pitch += 360;
        } else {
            prevPitch = 180.0f;
            pitch = prevPitch;
        }
        if (isSubmergedInWater() && !extinguished) {
            extinguished = true;
            SoundHelper.playSound(world, x, y, z, "random.fizz", 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
            for (int k = 0; k < 8; ++k) {
                float f6 = 0.25f;
                ParticleHelper.addParticle(world, "explode", x - velocityX * f6,
                        y - velocityY * f6, z - velocityZ * f6, velocityX, velocityY,
                        velocityZ);
            }
        }
        --explodefuse;
        if (!extinguished) {
            if (explodefuse <= 0) {
                detonate();
                markDead();
            } else {
                ParticleHelper.addParticle(world, "smoke", x, y, z, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void onBlockHit(HitResult hitResult) {
        blockX = hitResult.blockX;
        blockY = hitResult.blockY;
        blockZ = hitResult.blockZ;
        inBlockState = world.getBlockState(blockX, blockY, blockZ);
        inMeta = world.getBlockMeta(blockX, blockY, blockZ);
        velocityX = hitResult.pos.x - x;
        velocityY = hitResult.pos.y - y;
        velocityZ = hitResult.pos.z - z;
        float f1 = MathHelper.sqrt(velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ);
        x -= velocityX / f1 * 0.05;
        y -= velocityY / f1 * 0.05;
        z -= velocityZ / f1 * 0.05;
        velocityX *= -0.2;
        velocityZ *= -0.2;
        if (hitResult.side == 1) {
            inGround = true;
            beenInGround = true;
        } else {
            inGround = false;
            SoundHelper.playSound(world, x, y, z, "random.fizz", 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
        }
        if (inBlockState != null && inBlockState != States.AIR) {
            inBlockState.getBlock().onEntityCollision(world, blockX, blockY, blockZ, this);
        }
    }

    private void detonate() {
        if (world.isRemote) {
            return;
        }
        if (extinguished && (ticksInGround >= 200 || ticksInAir >= 200)) {
            markDead();
        }
        float f = 2.0f;
        PhysHelper.createAdvancedExplosion(world, this, x, y, z, f,
                true /** BalkonsWeaponMod.instance.modConfig.dynamiteDoesBlockDamage*/, true, false, false);
    }

    @Override
    public boolean aimRotation() {
        return false;
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }

    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeapons.dynamite);
    }

    @Override
    public void playHitSound() {
        SoundHelper.playSound(world, x, y, z, "random.fizz", 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putByte("fuse", (byte) explodefuse);
        nbt.putBoolean("off", extinguished);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        explodefuse = nbt.getByte("fuse");
        extinguished = nbt.getBoolean("off");
    }
}
