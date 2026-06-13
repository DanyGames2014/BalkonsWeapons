package net.danygames2014.balkonsweapons.entity;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.balkonsweapons.entity.projectile.ProjectileEntity;
import net.danygames2014.balkonsweapons.util.EntityUtil;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class DummyEntity extends Entity {
    private static final int TIME_SINCE_HIT = 17;
    private static final int ROCK_DIRECTION = 18;
    private static final int CURRENT_DAMAGE = 19;
    private int durability;

    public DummyEntity(World world) {
        super(world);
        pitch = -20.0F;
        setRotation(yaw, pitch);
        setBoundingBoxSpacing(0.5F, 1.9F);
        durability = 50;
    }

    public DummyEntity(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
        velocityX = 0.0D;
        velocityY = 0.0D;
        velocityZ = 0.0D;
        prevX = x;
        prevY = y;
        prevZ = z;
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(TIME_SINCE_HIT, 0);
        dataTracker.startTracking(ROCK_DIRECTION, (byte) 1);
        dataTracker.startTracking(CURRENT_DAMAGE, 0);
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
    public boolean damage(Entity damageSource, int amount) {
        if (world.isRemote || dead || amount <= 0.0f) {
            return false;
        }
        setRockDirection(-getRockDirection());
        setTimeSinceHit(10);
        int i = getCurrentDamage();
        i += (int) (amount * 5.0f);
        if (i > 50) {
            i = 50;
        }
        setCurrentDamage(i);
        scheduleVelocityUpdate();
        if (!(damageSource instanceof Entity)) {
            durability -= amount;
        } else if (damageSource instanceof ProjectileEntity projectileEntity) {
            if (MathHelper.sqrt(projectileEntity.velocityX * projectileEntity.velocityX + projectileEntity.velocityY * projectileEntity.velocityY + projectileEntity.velocityZ * projectileEntity.velocityZ) > 0.5) {
                projectileEntity.velocityX *= 0.10000000149011612;
                projectileEntity.velocityY *= 0.10000000149011612;
                projectileEntity.velocityZ *= 0.10000000149011612;
                playRandomHitSound();
            } else {
                projectileEntity.velocityX = random.nextFloat() - 0.5f;
                projectileEntity.velocityY = random.nextFloat() - 0.5f;
                projectileEntity.velocityZ = random.nextFloat() - 0.5f;
            }
        } else {
            playRandomHitSound();
        }
        if (durability <= 0) {
            dropAsItem(true, true);
        }
        scheduleVelocityUpdate();
        return false;
    }

    public void playRandomHitSound() {
        int i = random.nextInt(2);
        if (i == 0) {
            SoundHelper.playSound(world, x, y, z, "step.cloth", 0.7F, 1F / random.nextFloat() * 0.2F + 0.4F);
        } else {
            SoundHelper.playSound(world, x, y, z, "step.wood", 0.7F, 1F / random.nextFloat() * 0.2F + 0.2F);
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
        super.tick();
        int i = getTimeSinceHit();
        if (i > 0) {
            setTimeSinceHit(i - 1);
        }
        i = getCurrentDamage();
        if (i > 0) {
            setCurrentDamage(i - random.nextInt(2));
        }
        prevX = x;
        prevY = y;
        prevZ = z;
        if (onGround) {
            velocityX = 0.0;
            velocityY = 0.0;
            velocityZ = 0.0;
        } else {
            velocityX *= 0.99;
            velocityZ *= 0.99;
            velocityY -= 0.05;
            fallDistance += (float) (-velocityY);
        }
        setRotation(yaw, pitch);
        move(0.0, velocityY, 0.0);
        List<Entity> list = world.getEntities(this,
                EntityUtil.getBoundingBox(this).expand(0.2D, 0.0D, 0.2D));
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (entity != passenger && entity.isPushable()) {
                    onCollision(entity);
                }
            }
        }
    }

    @Override
    protected void onLanding(float fallDistance) {
        super.onLanding(fallDistance);
        if (!onGround) {
            return;
        }
        int i = MathHelper.floor(fallDistance);
        damage(null, i);
    }

    public void dropAsItem(boolean destroyed, boolean noCreative) {
        if (world.isRemote) {
            return;
        }
        if (destroyed) {
            for (int i = 0; i < random.nextInt(8); ++i) {
                dropItem(Item.LEATHER.id, 1);
            }
        } else if (noCreative) {
            dropItem(BalkonsWeapons.trainingDummy.id, 1);
        }
        markDead();
    }

    @Override
    public boolean interact(PlayerEntity player) {
        ItemStack stack = player.getHand();
        if(stack != null) { // this originally only cancelled  if you were holding a weapon, but I think just having an empty hand is better
            return false;
        }

        if(false) {  // creative check
            dropAsItem(false, false);
            return true;
        }

        dropAsItem(false, true);
        return true;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {

    }

    @Override
    protected void readNbt(NbtCompound nbt) {
        setPosition(x, y, z);
        setRotation(yaw, pitch);
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
