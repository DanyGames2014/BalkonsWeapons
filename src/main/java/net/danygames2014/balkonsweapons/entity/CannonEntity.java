package net.danygames2014.balkonsweapons.entity;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.balkonsweapons.entity.projectile.CannonBallEntity;
import net.danygames2014.balkonsweapons.util.EntityUtil;
import net.danygames2014.balkonsweapons.util.ItemUtil;
import net.danygames2014.nyalib.particle.ParticleHelper;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class CannonEntity extends BoatEntity {

    private static final int TIME_SINCE_HIT = 20;
    private static final int ROCK_DIRECTION = 21;
    private static final int CURRENT_DAMAGE = 22;
    private static final int LOADED = 23;
    private static final int LOAD_TIMER = 24;
    private static final int SUPER_POWERED = 25;

    public CannonEntity(World world) {
        super(world);
        blocksSameBlockSpawning = true;
        pitch = -20.0F;
        setRotation(yaw = 180.0F, pitch);
        setBoundingBoxSpacing(1.5F, 1.0F);
    }

    public CannonEntity(World world, double x, double y, double z) {
        this(world);
        // TODO: check out if yoffset is needed here
        setPosition(x, y + getStandingEyeHeight(), z);
        velocityX = 0.0;
        velocityY = 0.0;
        velocityZ = 0.0;
        prevX = x;
        prevY = y;
        prevZ = z;
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(TIME_SINCE_HIT, 0);
        dataTracker.startTracking(ROCK_DIRECTION, (byte) 1);
        dataTracker.startTracking(CURRENT_DAMAGE, 0);
        dataTracker.startTracking(LOADED, (byte) 0);
        dataTracker.startTracking(LOAD_TIMER, 0);
        dataTracker.startTracking(SUPER_POWERED, (byte) 0);
    }

    @Override
    public Box getCollisionAgainstShape(Entity other) {
        return EntityUtil.getBoundingBox(other);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public double getPassengerRidingHeight() {
        return 0.15D;
    }

    @Override
    public boolean damage(Entity damageSource, int amount) {
        if (world.isRemote || dead) {
            return true;
        }
        if (damageSource == passenger) {
            return true;
        } else if (damageSource instanceof PlayerEntity player) {
            if (player != null && player.inventory.getSelectedItem() == null) {
                if (true) { //creative check
                    dropItem(BalkonsWeapons.cannon.id, 1);
                    if (isLoaded() || isLoading()) {
                        dropItem(BalkonsWeapons.cannonBall.id, 1);
                        dropItem(Item.GUNPOWDER.id, 1);
                    }
                }
                markDead();
                return true;
            }
        }
        setRockDirection(-getRockDirection());
        setTimeSinceHit(10);
        setCurrentDamage(getCurrentDamage() + (int) amount * 5);
        scheduleVelocityUpdate();
        if (getCurrentDamage() > 100) {
            for (int j = 0; j < 6; ++j) {
                dropItemWithChance(Item.IRON_INGOT, amount, 1);
            }
            dropItemWithChance(Item.FLINT, amount, 1);
            dropItemWithChance(Block.LOG.asItem(), amount, 1);
            if (isLoaded() || isLoading()) {
                dropItem(BalkonsWeapons.cannonBall.id, 1);
                dropItem(Item.GUNPOWDER.id, 1);
            }
            markDead();
        }
        return true;
    }

    public void dropItemWithChance(Item item, int chance, int amount) {
        if (random.nextInt(chance) < 10) {
            dropItem(item.id, amount);
        }
    }

    @Override
    public void animateHurt() {
        setRockDirection(-getRockDirection());
        setTimeSinceHit(10);
        setCurrentDamage(getCurrentDamage() + 10);
    }

    @Override
    public boolean isCollidable() {
        return !dead;
    }

    @Override
    public void tick() {
        baseTick();
    }

    @Override
    public void baseTick() {
        super.baseTick();
        int i = getTimeSinceHit();
        if (i > 0) {
            setTimeSinceHit(i - 1);
        }
        i = getCurrentDamage();
        if (i > 0) {
            setCurrentDamage(i - random.nextInt(2));
        }
        velocityY -= 0.1;
        if (onGround) {
            velocityX *= 0.1;
            velocityZ *= 0.1;
        }
        velocityX *= 0.98;
        velocityY *= 0.98;
        velocityZ *= 0.98;
        if (!onGround) {
            fallDistance += (float) (-velocityY);
        }
        if (passenger != null) {
            float passengerYaw = passenger.yaw;
            float passengerPitch = passenger.pitch;
            yaw = passengerYaw % 360.0f;
            pitch = passengerPitch;
        }
        setRotation(yaw, pitch);
        move(velocityX, velocityY, velocityZ);

        List<Entity> list = world.getEntities(this,
                EntityUtil.getBoundingBox(this).expand(0.2, 0.0, 0.2));
        if (list != null) {
            for (Entity entity : list) {
                if (entity != passenger && entity.isPushable()) {
                    onCollision(entity);
                }
            }
        }
        if (passenger != null) {
            if (passenger.dead) {
                passenger = null;
            }
        }
        if (isLoading()) {
            setLoadTimer(getLoadTimer() - 1);
            handleReloadTime();
        }
    }

    @Override
    protected void onLanding(float fallDistance) {
        super.onLanding(fallDistance);
        int i = MathHelper.floor(fallDistance);
        i *= 2;
        damage(null, i);
    }

    @Override
    protected void fall(double heightDifference, boolean onGround) {
        int x = MathHelper.floor(this.x);
        int y = MathHelper.floor(this.y);
        int z = MathHelper.floor(this.z);
        if (onGround) {
            if (fallDistance > 3.0F) {
                onLanding(fallDistance);
                if (!world.isRemote && !dead) {
                    markDead();
                    for (int j = 0; j < 5; ++j) {
                        // Yes, one iron ingot should vanish as penalty...
                        dropItem(Item.IRON_INGOT.id, 1, 0.0F);
                    }
                    dropItem(Item.FLINT.id, 1, 0.0F);
                    dropItem(Block.LOG.asItem().id, 1, 0.0F);
                    if (isLoaded() || isLoading()) {
                        dropItem(BalkonsWeapons.cannonBall.id, 1, 0.0F);
                        dropItem(Item.GUNPOWDER.id, 1, 0.0F);
                    }
                }

                fallDistance = 0.0F;
            }
        } else if (world.getBlockState(x, y - 1, z).getMaterial() != Material.WATER && heightDifference < 0.0) {
            fallDistance = (float) ((double) fallDistance - heightDifference);
        }
    }

    public void handleReloadTime() {
        int l = getLoadTimer();
        if (l > 0) {
            if (l == 80 || l == 70 || l == 60) {
                SoundHelper.playSound(world, x, y, z, "tile.piston.in", 0.5f, 1.2f / (random.nextFloat() * 0.8f + 0.6f));
            } else if (l == 40) {
                SoundHelper.playSound(world, x, y, z, "random.breath", 0.7f, 1.2f / (random.nextFloat() * 0.2f + 10.0f));
            }
        } else {
            setReloadInfo(true, 0);
        }
    }

    public void fireCannon() {
        if (!isLoaded()) {
            return;
        }
        if (!world.isRemote) {
            CannonBallEntity cannonBallEntity = new CannonBallEntity(world, this,
                    passenger.pitch, passenger.yaw, isSuperPowered());
            world.spawnEntity(cannonBallEntity);
        }
        setReloadInfo(false, 0);
        fireEffects();
    }

    public void fireEffects() {
        SoundHelper.playSound(world, x, y, z, "random.explode", 8.0F, 1.0F / (random.nextFloat() * 0.8F + 0.9F));
        SoundHelper.playSound(world, x, y, z, "ambient.weather.thunder", 8.0F, 1.0F / (random.nextFloat() * 0.4F + 0.6F));
        float yaw2 = (float) Math.toRadians(yaw);
        double d = -MathHelper.sin(yaw2) * -1.0f;
        double d2 = MathHelper.cos(yaw2) * -1.0f;
        for (int i = 0; i < 20; ++i) {
            ParticleHelper.addParticle(world, "smoke",
                    x + d + random.nextDouble() * 0.5 - 0.25, y + random.nextDouble() * 0.5,
                    z + d2 + random.nextDouble() * 0.5 - 0.25, random.nextDouble() * 0.1 - 0.05,
                    random.nextDouble() * 0.1 - 0.05, random.nextDouble() * 0.1 - 0.05);
        }
        if (passenger != null) {
            passenger.pitch += 10.0f;
        }
        damage(null, 2);
    }

    public void setReloadInfo(boolean loaded, int reloadtime) {
        setLoaded(loaded);
        setLoadTimer(reloadtime);
    }

    public void startLoadingCannon() {
        if (isLoaded() && !isLoading()) {
            return;
        }
        setReloadInfo(false, 100);
    }

    @Override
    public void updatePassengerPosition() {
        if (passenger != null) {
            float f = -0.85f;
            float f2 = (float) ((dead ? 0.01 : getPassengerRidingHeight()) + passenger.getStandingEyeHeight());
            Vec3d vec3d = Vec3d.create(f, 0.0, 0.0);
            vec3d.rotateY(-yaw * 0.017453292f - 1.5707964f);
            passenger.setPosition(x + vec3d.x, y + f2, z + vec3d.z);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putFloat("falld", fallDistance);
        nbt.putBoolean("load", isLoaded());
        nbt.putShort("ldtime", (short) getLoadTimer());
    }

    @Override
    protected void readNbt(NbtCompound nbt) {
        setPosition(x, y, z);
        setRotation(yaw, pitch);
        fallDistance = nbt.getFloat("falld");
        setLoaded(nbt.getBoolean("load"));
        setLoadTimer(nbt.getShort("ldtime"));
    }

    @Override
    public boolean interact(PlayerEntity player) {
        ItemStack itemstack = player.getHand();
        if (itemstack != null && itemstack.getItem() == BalkonsWeapons.cannonBall && !isLoaded() && !isLoading()
                    && (false /** player.capabilities.isCreativeMode */ || consumeAmmo(player, Item.GUNPOWDER))) {
            if (false /** player.capabilities.isCreativeMode */ || consumeAmmo(player, BalkonsWeapons.cannonBall)) {
                startLoadingCannon();
                return true;
            }
            dropItem(Item.GUNPOWDER.id, 1);
        } else {
            if (passenger != null && riddenByPlayer() && notThisPlayer(player)) {
                return true;
            }
            if (!world.isRemote && !player.isSneaking()) {
                player.setVehicle(this);
            }
        }
        return true;
    }

    protected boolean consumeAmmo(PlayerEntity player, Item ammo) {
        return ItemUtil.consumeInventoryItem(player, ammo);
    }

    public boolean riddenByPlayer() {
        Entity entity = passenger;
        return entity instanceof PlayerEntity;
    }

    public boolean notThisPlayer(Entity player) {
        Entity entity = passenger;
        return entity != player;
    }

    @Override
    public void onStruckByLightning(LightningEntity lightning) {
//        dealFireDamage(100);
        setSuperPowered(true);
    }

    public void setLoaded(boolean flag) {
        dataTracker.set(LOADED, (byte) (flag ? 1 : 0));
    }

    public void setLoadTimer(int time) {
        dataTracker.set(LOAD_TIMER, time);
    }

    public void setSuperPowered(boolean flag) {
        dataTracker.set(SUPER_POWERED, (byte) (flag ? 1 : 0));
    }

    public boolean isLoading() {
        return getLoadTimer() > 0;
    }

    public boolean isLoaded() {
        return dataTracker.getByte(LOADED) != 0;
    }

    public int getLoadTimer() {
        return dataTracker.getInt(LOAD_TIMER);
    }

    public boolean isSuperPowered() {
        return dataTracker.getByte(SUPER_POWERED) != 0;
    }

    public void setTimeSinceHit(int i) {
        dataTracker.set(TIME_SINCE_HIT, i);
    }

    public void setRockDirection(int i) {
        dataTracker.set(ROCK_DIRECTION, (byte) i);
    }

    public void setCurrentDamage(int i) {
        dataTracker.set(CURRENT_DAMAGE, i);
    }

    public int getTimeSinceHit() {
        return dataTracker.getInt(TIME_SINCE_HIT);
    }

    public int getRockDirection() {
        return dataTracker.getByte(ROCK_DIRECTION);
    }

    public int getCurrentDamage() {
        return dataTracker.getInt(CURRENT_DAMAGE);
    }


}
