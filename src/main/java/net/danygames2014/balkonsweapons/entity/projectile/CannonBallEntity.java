package net.danygames2014.balkonsweapons.entity.projectile;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.balkonsweapons.config.Config;
import net.danygames2014.balkonsweapons.entity.CannonEntity;
import net.danygames2014.balkonsweapons.util.PhysHelper;
import net.danygames2014.nyalib.particle.ParticleHelper;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class CannonBallEntity extends ProjectileEntity{
    public CannonBallEntity(World world) {
        super(world);
    }

    public CannonBallEntity(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
    }

    public CannonBallEntity(World world, CannonEntity cannonEntity, float f, float f1, boolean superPowered) {
        this(world, cannonEntity.x, cannonEntity.y + 1.0, cannonEntity.z);
        Entity entityPassenger = cannonEntity.passenger;
        if (entityPassenger instanceof LivingEntity livingEntity) {
            setThrower(livingEntity);
            setPickupStatusFromEntity(livingEntity);
        } else {
            setPickupStatus(PickupStatus.ALLOWED);
        }
        setBoundingBoxSpacing(0.5f, 0.5f);
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        setVelocity(x, y, z, superPowered ? 4.0f : 2.0f, superPowered ? 0.1f : 2.0f);
        velocityX += cannonEntity.velocityX;
        velocityY += cannonEntity.velocityY;
        setIsCritical(superPowered);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        double speed = MathHelper.sqrt(velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ);
        double amount = 8.0;
        if (speed > 1.0) {
            for (int i1 = 1; i1 < amount; ++i1) {
                ParticleHelper.addParticle(world, "smoke", x + velocityX * i1 / amount, y + velocityY * i1 / amount, z + velocityZ * i1 / amount, 0.0, 0.0, 0.0);
            }
        }
    }

    public void createCrater() {
        if (world.isRemote || !inGround || isSubmergedInWater()) {
            return;
        }
        markDead();
        float f = getIsCritical() ? 5.0f : 2.5f;
        PhysHelper.createAdvancedExplosion(world, this, x, y, z, f,
                Config.BLOCK_DAMAGE_CONFIG.cannonBlockDamage, true, false, false);
    }

    @Override
    public void onEntityHit(HitResult hitResult) {
        Entity entity = hitResult.entity;
        if (entity.damage(getDamagingEntity(), 30)) {
            SoundHelper.playSound(world, x, y, z, "random.hurt", 1.0F, 1.2F / (random.nextFloat() * 0.4F + 0.7F));
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
        if (inBlockState != null) {
            inBlockState.getBlock().onEntityCollision(world, blockX, blockY, blockZ, this);
        }
        createCrater();
    }

    @Override
    public boolean canBeCritical() {
        return true;
    }

    @Override
    public float getAirResistance() {
        return 0.98F;
    }

    @Override
    public float getGravity() {
        return 0.04f;
    }

    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeapons.cannonBall);
    }
}
