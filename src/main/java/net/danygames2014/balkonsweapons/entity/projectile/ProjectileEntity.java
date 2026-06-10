package net.danygames2014.balkonsweapons.entity.projectile;

import com.mojang.serialization.DataResult;
import net.danygames2014.balkonsweapons.util.EntityUtil;
import net.danygames2014.nyalib.particle.ParticleHelper;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.States;
import net.modificationstation.stationapi.api.nbt.NbtOps;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ProjectileEntity extends ArrowEntity implements EntitySpawnDataProvider {
    private static final int WEAPON_CRITICAL = 17;
    protected int blockX;
    protected int blockY;
    protected int blockZ;

    @Nullable
    protected BlockState inBlockState;
    protected int inMeta;
    protected boolean inGround;
    public PickupStatus pickupStatus;
    protected int ticksInGround;
    protected int ticksInAir;
    public boolean beenInGround;
    public float extraDamage;
    public int knockback;

    public ProjectileEntity(World world) {
        super(world);
        blockX = -1;
        blockY = -1;
        blockZ = -1;
        inBlockState = null;
        inGround = false;
        shake = 0;
        ticksInAir = 0;
        pickupStatus = PickupStatus.DISALLOWED;
        extraDamage = 0.0f;
        knockback = 0;
        setBoundingBoxSpacing(0.5F, 0.5F);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(WEAPON_CRITICAL, (byte)0);
    }

    public Entity getThrower() {
        return owner;
    }

    public void setThrower(LivingEntity entity) {
        owner = entity;
    }

    public void setAim(Entity entity, float f, float f1, float f2, float f3, float f4) {
    }

    protected void setPickupStatusFromEntity(LivingEntity livingEntity) {
        if(livingEntity instanceof PlayerEntity player) {
            if(false) { // creative check
                setPickupStatus(PickupStatus.CREATIVE_ONLY);
            } else {
                setPickupStatus(true ? PickupStatus.ALLOWED : PickupStatus.OWNER_ONLY); // TODO: BalkonsWeaponMod.instance.modConfig.allCanPickup
            }

        } else {
            setPickupStatus(PickupStatus.DISALLOWED);
        }
    }

    @Override
    public void setVelocity(double x, double y, double z, float speed, float divergence) {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x /= f;
        y /= f;
        z /= f;
        x += random.nextGaussian() * 0.007499999832361937 * divergence;
        y += random.nextGaussian() * 0.007499999832361937 * divergence;
        z += random.nextGaussian() * 0.007499999832361937 * divergence;
        x *= speed;
        y *= speed;
        z *= speed;
        velocityX = x;
        velocityY = y;
        velocityZ = z;
        float f2 = MathHelper.sqrt(x * x + z * z);
        float n = (float) (Math.atan2(x, z) * 180.0 / 3.141592653589793);
        yaw = n;
        prevYaw = n;
        float n2 = (float) (Math.atan2(y, f2) * 180.0 / 3.141592653589793);
        pitch = n2;
        prevPitch = n2;
        ticksInGround = 0;
    }

    @Override
    public void setVelocityClient(double x, double y, double z) {
        velocityX = x;
        velocityY = y;
        velocityZ = z;
        if (aimRotation() && prevPitch == 0.0f && prevYaw == 0.0f) {
            float f = MathHelper.sqrt(x * x + z * z);
            float n = (float) (Math.atan2(x, z) * 180.0 / 3.141592653589793);
            yaw = n;
            prevYaw = n;
            float n2 = (float) (Math.atan2(y, f) * 180.0 / 3.141592653589793);
            pitch = n2;
            prevPitch = n2;
            setPositionAndAngles(x, y, z, yaw, pitch);
            ticksInGround = 0;
        }
    }

    @Override
    public void tick() {
        baseTick();
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (aimRotation() && prevPitch == 0.0f && prevYaw == 0.0f) {
            float f = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ);
            float n = (float) (Math.atan2(velocityX, velocityZ) * 180.0 / 3.141592653589793);
            yaw = n;
            prevYaw = n;
            float n2 = (float) (Math.atan2(velocityY, f) * 180.0 / 3.141592653589793);
            pitch = n2;
            prevPitch = n2;
        }
        BlockState block = world.getBlockState(blockX, blockY, blockZ);
        if (block != null) {
            block.getBlock().updateBoundingBox(world, blockX, blockY, blockZ);
            Box box = block.getBlock().getCollisionShape(world, blockX, blockY, blockZ);
            if (box != null && box.contains(Vec3d.create(blockX, blockY, blockZ))) {
                inGround = true;
            }
        }
        if (shake > 0) {
            --shake;
        }
        if (isWet()) {
            this.fireTicks = 0;
        }
        if (inGround) {
            int i = world.getBlockMeta(blockX, blockY, blockZ);
            if (block != inBlockState || i != inMeta) {
                inGround = false;
                velocityX *= random.nextFloat() * 0.2f;
                velocityY *= random.nextFloat() * 0.2f;
                velocityZ *= random.nextFloat() * 0.2f;
                ticksInGround = 0;
                ticksInAir = 0;
            } else if (!world.isRemote) {
                ++ticksInGround;
                int t = getMaxLifetime();
                if (t != 0 && ticksInGround >= t) {
                    markDead();
                }
            }
            ++ticksInGround;
            return;
        }
        ticksInGround = 0;
        ++ticksInAir;
        Vec3d vec3d = Vec3d.create(x, y, z);
        Vec3d vec3d2 = Vec3d.create(x + velocityX, y + velocityY, z + velocityZ);
        HitResult hitResult = world.raycast(vec3d, vec3d2, false, true);
        vec3d = Vec3d.create(x, y, z);
        vec3d2 = Vec3d.create(x + velocityX, y + velocityY, z + velocityZ);
        if (hitResult != null) {
            vec3d2 = Vec3d.create(hitResult.pos.x, hitResult.pos.y,
                    hitResult.pos.z);
        }
        Entity entity = findEntity(vec3d, vec3d2);
        if (entity != null) {
            hitResult = new HitResult(entity);
        }
        if (hitResult != null) {
            onHit(hitResult);
        }
        if (getIsCritical()) {
            for (int i1 = 0; i1 < 2; ++i1) {
                ParticleHelper.addParticle(world, "crit", x + velocityX * i1 / 4.0,
                        y + velocityY * i1 / 4.0, z + velocityZ * i1 / 4.0, -velocityX,
                        -velocityY + 0.2, -velocityZ);
            }
        }
        x += velocityX;
        y += velocityY;
        z += velocityZ;
        if (aimRotation()) {
            float f2 = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ);
            float n3 = (float) (Math.atan2(velocityX, velocityZ) * 180.0 / 3.141592653589793);
            yaw = n3;
            prevYaw = n3;
            float n4 = (float) (Math.atan2(velocityY, f2) * 180.0 / 3.141592653589793);
            pitch = n4;
            prevPitch = n4;
        }
        float res = getAirResistance();
        float grav = getGravity();
        if (isSubmergedInWater()) {
            beenInGround = true;
            for (int i2 = 0; i2 < 4; ++i2) {
                float f3 = 0.25f;
                ParticleHelper.addParticle(world, "bubble", x - velocityX * f3,
                        y - velocityY * f3, z - velocityZ * f3, velocityX, velocityY,
                        velocityZ);
            }
            res *= 0.6f;
        }
        velocityX *= res;
        velocityY *= res;
        velocityZ *= res;
        velocityY -= grav;
        setPosition(x, y, z);
        notifyBlocks();
    }

    protected void onHit(HitResult hitResult) {
        if(hitResult.entity != null) {
            onEntityHit(hitResult);
        } else {
            onBlockHit(hitResult);
        }
    }

    public void onEntityHit(HitResult hitResult) {
        bounceBack();
        applyEntityHitEffects(hitResult.entity);
    }

    public void applyEntityHitEffects(Entity entity) {
        if(onFire()) {
            EntityUtil.setFire(entity, 5);
        }
        if(entity instanceof LivingEntity livingEntity) {
            if(knockback > 0) {
                float f = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ);
                if(f > 0.0F) {
                    entity.addVelocity(velocityX * knockback * 0.6 / f, 0.1,
                            velocityZ * knockback * 0.6 / f);
                }
            }
        }

        // TODO: check if this is needed
//        if (shootingEntity instanceof EntityPlayerMP && shootingEntity != entity && entity instanceof EntityPlayer) {
//            ((EntityPlayerMP) shootingEntity).playerNetServerHandler.sendPacket(
//                    new S2BPacketChangeGameState(6, 0.0f));
//        }
    }

    public void onBlockHit(HitResult hitResult) {
        blockX = hitResult.blockX;
        blockY = hitResult.blockY;
        blockZ = hitResult.blockZ;

        inBlockState = world.getBlockState(blockX, blockY, blockZ);
        inMeta = world.getBlockMeta(blockX, blockY, blockZ);

        float f1 = MathHelper.sqrt(velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ);

        x -= velocityX / f1 * 0.05;
        y -= velocityY / f1 * 0.05;
        z -= velocityZ / f1 * 0.05;
        inGround = true;
        beenInGround = true;
        setIsCritical(false);
        shake = getMaxArrowShake();
        playHitSound();
        if (inBlockState != null && inBlockState != States.AIR) {
            inBlockState.getBlock().onEntityCollision(world, blockX, blockY, blockZ, this);
        }
    }

    private boolean onFire() {
        return this.fireTicks > 0 || this.getFlag(0);
    }

    protected void bounceBack() {
        velocityX *= -0.1;
        velocityY *= -0.1;
        velocityZ *= -0.1;
        yaw += 180.0f;
        prevYaw += 180.0f;
        ticksInAir = 0;
    }

    @Nullable
    protected Entity findEntity(Vec3d vec3d, Vec3d vec3d1) {
        Entity entity = null;
        List<Entity> list = world.getEntities(this,
                EntityUtil.getBoundingBox(this).stretch(velocityX, velocityY, velocityZ).expand(1.0, 1.0, 1.0));
        double d = 0.0;
        for (Entity entity2 : list) {
            if (entity2.isCollidable() && (entity2 != owner || ticksInAir >= 5)) {
                double exp = 0.3;
                Box box = EntityUtil.getBoundingBox(entity2).expand(exp, exp, exp);
                HitResult hit = box.raycast(vec3d, vec3d1);
                if (hit != null) {
                    double d2 = vec3d.squaredDistanceTo(hit.pos);
                    if (d2 < d || d == 0.0) {
                        entity = entity2;
                        d = d2;
                    }
                }
            }
        }
        return entity;
    }

    public double getTotalVelocity() {
        return MathHelper.sqrt(velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ);
    }

    public boolean aimRotation() {
        return true;
    }

    public int getMaxLifetime() {
        return 1200;
    }

    public ItemStack getPickupItem() {
        return null;
    }

    public ItemStack getPickedResult(HitResult target) {
        return getPickupItem();
    }

    public float getAirResistance() {
        return 0.99f;
    }

    public float getGravity() {
        return 0.05f;
    }

    public int getMaxArrowShake() {
        return 7;
    }

    public void playHitSound() {
    }

    public boolean canBeCritical() {
        return false;
    }

    public void setIsCritical(boolean flag) {
        if (canBeCritical()) {
            dataTracker.set(WEAPON_CRITICAL, (byte) (flag ? 1 : 0));
        }
    }

    public boolean getIsCritical() {
        return canBeCritical() && dataTracker.getByte(WEAPON_CRITICAL) != 0;
    }

    public void setExtraDamage(float f) {
        extraDamage = f;
    }

    public void setKnockbackStrength(int i) {
        knockback = i;
    }

    public void setPickupStatus(PickupStatus i) {
        pickupStatus = i;
    }

    public PickupStatus getPickupStatus() {
        return pickupStatus;
    }

    public boolean canPickup(PlayerEntity entityplayer) {
        if (pickupStatus == PickupStatus.ALLOWED) {
            return true;
        }
        if (pickupStatus == PickupStatus.CREATIVE_ONLY) {
            return false; // entityplayer.capabilities.isCreativeMode
        }
        return pickupStatus == PickupStatus.OWNER_ONLY && entityplayer == owner;
    }

    @Override
    public void onPlayerInteraction(PlayerEntity player) {
        if (inGround && shake <= 0 && canPickup(player) && !world.isRemote) {
            ItemStack item = getPickupItem();
            if (item == null) {
                return;
            }
            if ((pickupStatus == PickupStatus.CREATIVE_ONLY && false) ||
                        player.inventory.addStack(item)) {
                SoundHelper.playSound(world, "random.pop", 0.2f, ((random.nextFloat() - random.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                onItemPickup(player);
                markDead();
            }
        }
    }

    protected void onItemPickup(PlayerEntity player) {
        player.sendPickup(this, 1);
    }

    @Override
    public float getEyeHeight() {
        return 0.0F;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("blockX", blockX);
        nbt.putInt("blockY", blockY);
        nbt.putInt("blockZ", blockZ);

        // this feels so overkill
        if(inBlockState != null) {
            DataResult<NbtElement> encoded = BlockState.CODEC.encodeStart(NbtOps.INSTANCE, inBlockState);
            encoded.result().ifPresent(nbtElement -> {
                if(nbtElement instanceof NbtCompound compound) {
                    nbt.put("inBlockState", compound);
                }
            });
        }

        nbt.putByte("inMeta", (byte)inMeta);
        nbt.putByte("shake", (byte)shake);
        nbt.putBoolean("inGround", inGround);
        nbt.putBoolean("beenInGround", beenInGround);
        nbt.putByte("pickup", (byte) pickupStatus.ordinal());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        blockX = nbt.getInt("blockX");
        blockY = nbt.getInt("blockY");
        blockZ = nbt.getInt("blockZ");

        if(nbt.contains("inBlockState")) {
            NbtCompound compound = nbt.getCompound("inBlockState");
            inBlockState = BlockState.CODEC.parse(NbtOps.INSTANCE, compound).result().orElse(States.AIR.get());
        }

        inMeta = nbt.getByte("inMeta") & 0xFF;
        shake = nbt.getByte("shake") & 0xFF;
        inGround = nbt.getBoolean("inGround");
        beenInGround = nbt.getBoolean("beenInGround");
        pickupStatus = PickupStatus.getByOrdinal(nbt.getByte("pickup"));
    }

    private void notifyBlocks() {
        int var44 = MathHelper.floor(this.boundingBox.minX + 0.001);
        int var45 = MathHelper.floor(this.boundingBox.minY + 0.001);
        int var47 = MathHelper.floor(this.boundingBox.minZ + 0.001);
        int var53 = MathHelper.floor(this.boundingBox.maxX - 0.001);
        int var55 = MathHelper.floor(this.boundingBox.maxY - 0.001);
        int var30 = MathHelper.floor(this.boundingBox.maxZ - 0.001);
        if (this.world.isRegionLoaded(var44, var45, var47, var53, var55, var30)) {
            for(int var31 = var44; var31 <= var53; ++var31) {
                for(int var32 = var45; var32 <= var55; ++var32) {
                    for(int var33 = var47; var33 <= var30; ++var33) {
                        int var34 = this.world.getBlockId(var31, var32, var33);
                        if (var34 > 0) {
                            Block.BLOCKS[var34].onEntityCollision(this.world, var31, var32, var33, this);
                        }
                    }
                }
            }
        }
    }

    public enum PickupStatus {
        DISALLOWED,
        ALLOWED,
        CREATIVE_ONLY,
        OWNER_ONLY;

        public static PickupStatus getByOrdinal(int ordinal) {
            if (ordinal < 0 || ordinal > values().length) {
                ordinal = 0;
            }

            return values()[ordinal];
        }
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return null;
    }
}
