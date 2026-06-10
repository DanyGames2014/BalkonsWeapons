package net.danygames2014.balkonsweapons.entity.projectile;

import net.danygames2014.balkonsweapons.item.component.WeaponItem;
import net.danygames2014.balkonsweapons.util.DataTrackerUtil;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BoomerangEntity extends MaterialProjectileEntity{
    private static final int BOOMERANG = 20;
    public static final double RETURN_STRENGTH = 0.05;
    public static final float MIN_FLOAT_STRENGTH = 0.4f;
    private float soundTimer;
    public float floatStrength;

    public BoomerangEntity(World world) {
        super(world);
    }

    public BoomerangEntity(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
    }

    public BoomerangEntity(World world, LivingEntity shooter, ItemStack itemStack) {
        this(world, shooter.x, shooter.y + shooter.getEyeHeight() - 0.1, shooter.z);
        setThrower(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemStack);
        soundTimer = 0.0F;
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
        floatStrength = Math.min(1.5f, f3);
        dataTracker.set(BOOMERANG, floatStrength);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(BOOMERANG, 0.0F);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        floatStrength = DataTrackerUtil.getFloat(dataTracker, BOOMERANG);
        if (inGround) {
            return;
        }
        floatStrength *= 0.994f;
        if (floatStrength < MIN_FLOAT_STRENGTH) {
            if (getIsCritical()) {
                setIsCritical(false);
            }
            floatStrength = 0.0f;
        }
        float limitedStrength = Math.min(1.0f, floatStrength);
        if (!beenInGround) {
            yaw += 20.0f * floatStrength;
        }
        Entity shooter;
        if (!beenInGround && (shooter = getThrower()) != null && floatStrength > 0.0f) {
            double dx = x - shooter.x;
            double dy = y - shooter.y - shooter.getEyeHeight();
            double dz = z - shooter.z;
            double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
            dx /= d;
            dy /= d;
            dz /= d;
            velocityX -= RETURN_STRENGTH * dx;
            velocityY -= RETURN_STRENGTH * dy;
            velocityZ -= RETURN_STRENGTH * dz;
            soundTimer += limitedStrength;
            if (soundTimer > 3.0f) {
                SoundHelper.playSound(world, x, y, z, "random.bow", 0.6F,
                        1.0F / (random.nextFloat() * 0.2F + 2.2F - limitedStrength));
                soundTimer %= 3.0f;
            }
        }
        dataTracker.set(BOOMERANG, floatStrength);
    }

    @Override
    public void onEntityHit(HitResult hitResult) {
        if (world.isRemote || floatStrength < MIN_FLOAT_STRENGTH) {
            return;
        }
        Entity entity = hitResult.entity;
        Entity shooter = getThrower();
        if (entity == shooter) {
            if (entity instanceof PlayerEntity player) {
                ItemStack item = getPickupItem();
                if (item == null) {
                    return;
                }
                if (false || player.inventory.addStack(item)) {
                    SoundHelper.playSound(world, x, y, z, "random.pop", 0.2F,
                            ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    onItemPickup(player);
                    markDead();
                }
            }
            return;
        }
        ItemStack thrownItem = getWeapon();
        if (thrownItem == null || !(thrownItem.getItem() instanceof WeaponItem weaponItem)) return;
        float damage =
                weaponItem.getEntityDamage() + 2.0f;
        if (getIsCritical()) {
            damage += 2.0f;
        }
        if (entity.damage(getThrower(), (int)damage)) {
            applyEntityHitEffects(entity);
            playHitSound();
            if (thrownItem.getDamage() + 1 >= thrownItem.getMaxDamage()) {
                thrownItem.split(1);
                if (thrownItem.count <= 0) {
                    setThrownItemStack(null);
                }
                markDead();
            } else {
                thrownItem.setDamage(thrownItem.getDamage() + 1); // TODO: make sure to remove itemstack if its broken
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
        x -= velocityX / f1 * RETURN_STRENGTH;
        y -= velocityY / f1 * RETURN_STRENGTH;
        z -= velocityZ / f1 * RETURN_STRENGTH;
        velocityX *= -random.nextFloat() * 0.5f;
        velocityZ *= -random.nextFloat() * 0.5f;
        velocityY = random.nextFloat() * 0.1f;
        inGround = hitResult.side == 1;
        setIsCritical(false);
        beenInGround = true;
        floatStrength = 0.0f;
        if (inBlockState != null) {
            inBlockState.getBlock().onEntityCollision(world, blockX, blockY, blockZ, this);
        }
    }

    @Override
    public void playHitSound() {
        SoundHelper.playSound(world, x, y, z, "random.bowhit", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.9F));
    }

    @Override
    public boolean aimRotation() {
        return beenInGround || floatStrength < MIN_FLOAT_STRENGTH;
    }

    @Override
    public int getMaxLifetime() {
        return (pickupStatus == PickupStatus.ALLOWED || pickupStatus == PickupStatus.OWNER_ONLY) ? 0 : 1200;
    }

    @Override
    public boolean canBeCritical() {
        return true;
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }

    @Override
    public float getGravity() {
        return (beenInGround || floatStrength < MIN_FLOAT_STRENGTH) ? (float) RETURN_STRENGTH : 0.0f;
    }

    @Override
    public float getAirResistance() {
        return 0.98f;
    }

    @Override
    public void onPlayerInteraction(PlayerEntity player) {
        if (!beenInGround && ticksInAir > 5 && !world.isRemote && floatStrength >= MIN_FLOAT_STRENGTH &&
                    player == owner) {
            ItemStack item = getPickupItem();
            if (item == null) {
                return;
            }
            if (false || player.inventory.addStack(item)) {
                SoundHelper.playSound(world, x, y, z, "random.pop", 0.2F,
                        ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                onItemPickup(player);
                markDead();
                return;
            }
        }
        super.onPlayerInteraction(player);
    }
}
