package net.danygames2014.balkonsweapons.entity.projectile;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.balkonsweapons.util.PhysHelper;
import net.danygames2014.nyalib.particle.ParticleHelper;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.States;

public class MortarShellEntity extends ProjectileEntity{
    public float explosiveSize;

    public MortarShellEntity(World world) {
        super(world);
        explosiveSize = 2.0f;
    }

    public MortarShellEntity(World world, double x, double y, double z) {
        this(world);
        setPickupStatus(PickupStatus.ALLOWED);
        setPosition(x, y, z);
    }

    public MortarShellEntity(World world, LivingEntity shooter) {
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
    public void baseTick() {
        super.baseTick();
        double speed = MathHelper.sqrt(velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ);
        double amount = 8.0;
        if (speed > 1.0) {
            for (int i1 = 1; i1 < amount; ++i1) {
                ParticleHelper.addParticle(world, "smoke", x + velocityX * i1 / amount,
                        y + velocityY * i1 / amount, z + velocityZ * i1 / amount, 0.0, 0.0, 0.0);
            }
        }
    }

    public void createCrater() {
        if (world.isRemote || !inGround || isSubmergedInWater()) {
            return;
        }
        markDead();
        if (extraDamage > 0) {
            explosiveSize += extraDamage / 4.0f;
        }
        PhysHelper.createAdvancedExplosion(world, this, x, y, z, explosiveSize,
                true /** BalkonsWeaponMod.instance.modConfig.mortarDoesBlockDamage */, true, isOnFire(), false);
    }

    @Override
    public void onEntityHit(HitResult hitResult) {
        velocityX -= velocityX / 2.0;
        velocityZ -= velocityZ / 2.0;
        velocityY -= velocityY / 2.0;
        if (hitResult.entity.damage(getDamagingEntity(), 5)) {
            SoundHelper.playSound(world, x, y, z, "damage.hurtflesh", 1.0f, 1.2f / (random.nextFloat() * 0.4f + 0.7f));
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
        inGround = true;
        if (inBlockState != null && inBlockState != States.AIR) {
            inBlockState.getBlock().onEntityCollision(world, blockX, blockY, blockZ, this);
        }
        createCrater();
    }

    @Override
    public float getAirResistance() {
        return 0.98F;
    }

    @Override
    public float getGravity() {
        return 0.04F;
    }

    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeapons.mortarShell);
    }
}
