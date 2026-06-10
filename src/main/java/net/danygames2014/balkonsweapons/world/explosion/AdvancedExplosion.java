package net.danygames2014.balkonsweapons.world.explosion;

import com.google.common.collect.Sets;
import net.danygames2014.balkonsweapons.util.EntityUtil;
import net.danygames2014.nyalib.particle.ParticleHelper;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.States;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AdvancedExplosion extends Explosion {

    protected static final Random random = new Random();
    public final World world;
    public final Entity source;
    public final double x;
    public final double y;
    public final double z;
    public final float power;
    public final boolean flame;
    public final boolean smoke;

    protected boolean calculatedExplosion;

    public AdvancedExplosion(World world, Entity source, double x, double y, double z, float power, boolean flame, boolean smoke) {
        super(world, source, x, y, z, power);
        this.world = world;
        this.source = source;
        this.x = x;
        this.y = y;
        this.z = z;
        this.power = power;
        this.flame = flame;
        this.smoke = smoke;
    }

    public void entityExplosion() {
        float size = power * 2.0f;
        int k1 = MathHelper.floor(x - size - 1.0);
        int l1 = MathHelper.floor(x + size + 1.0);
        int i2 = MathHelper.floor(y - size - 1.0);
        int i3 = MathHelper.floor(y + size + 1.0);
        int j2 = MathHelper.floor(z - size - 1.0);
        int j3 = MathHelper.floor(z + size + 1.0);
        List<Entity> list = world.getEntities(source,
                Box.create(k1, i2, j2, l1, i3, j3));
        Vec3d vec31 = Vec3d.create(x, y, z);
        for (Entity entity : list) {
            double dr = entity.getDistance(x, y, z) / size;
            if (dr <= 1.0) {
                double dx = entity.x - x;
                double dy = entity.y - y;
                double dz = entity.z - z;
                double d = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
                if (d != 0.0) {
                    dx /= d;
                    dy /= d;
                    dz /= d;
                    double dens = world.getVisibilityRatio(vec31, EntityUtil.getBoundingBox(entity));
                    double var36 = (1.0 - dr) * dens;
                    int damage = (int) ((var36 * var36 + var36) / 2.0 * 8.0 * size + 1.0);
                    entity.damage(source, damage);
                    entity.velocityX += dx * var36;
                    entity.velocityY += dy * var36;
                    entity.velocityZ += dz * var36;
                }
            }
        }
    }

    public void blockExplosion() {
        if(!calculatedExplosion) {
            calculateBlockExplosion();
        }
        for(BlockPos blockPos : (HashSet<BlockPos>)damagedBlocks) {
            int x = blockPos.x;
            int y = blockPos.y;
            int z = blockPos.z;
            BlockState blockState = world.getBlockState(x, y, z);
            if (blockState != States.AIR && blockState.getBlock() != null) {
                blockState.getBlock().dropWithChance(world, x, y, z, blockState, world.getBlockMeta(x, y, z), 1.0f / power);
                world.setBlock(x, y, z, 0);
                blockState.getBlock().onDestroyedByExplosion(world, x, y, z);
            }
        }
    }

    public void addFlames() {
        if(!calculatedExplosion) {
            calculateBlockExplosion();
        }

        for(BlockPos blockPos : (HashSet<BlockPos>)damagedBlocks) {
            int x = blockPos.x;
            int y = blockPos.y;
            int z = blockPos.z;
            BlockState current = world.getBlockState(blockPos);
            if(current == States.AIR && world.getBlockState(x, y - 1, z) != States.AIR && current.getBlock().isOpaque() && random.nextInt(3) == 0) {
                world.setBlock(x, y, z, Block.FIRE.id);
            }
        }
    }

    public void addParticles(boolean smallParticles, boolean bigParticles) {
        SoundHelper.playSound(world, x, y, z, "random.explode", 4.0F, (1.0f + (world.random.nextFloat() - world.random.nextFloat()) * 0.2f) * 0.7f);

        if(bigParticles) {
            ParticleHelper.addParticle(world, "hugeexplosion", x, y, z, 0.0, 0.0, 0.0);
        }

        if(!smallParticles) {
            return;
        }

        if(!calculatedExplosion) {
            calculateBlockExplosion();
        }

        for(BlockPos blockPos : (HashSet<BlockPos>)damagedBlocks) {
            double px = blockPos.x + world.random.nextFloat();
            double py = blockPos.y + world.random.nextFloat();
            double pz = blockPos.z + world.random.nextFloat();
            double dx = px - x;
            double dy = py - y;
            double dz = pz - z;
            double distance = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
            dx /= distance;
            dy /= distance;
            dz /= distance;
            double d7 = 0.5 / (distance / power + 0.1);
            d7 *= world.random.nextFloat() * world.random.nextFloat() + 0.3f;
            dx *= d7;
            dy *= d7;
            dz *= d7;

            ParticleHelper.addParticle(world, "explode", (px + x) / 2.0, (py + y) / 2.0, (pz + z) / 2.0, dx, dy, dz);
            ParticleHelper.addParticle(world, "smoke", px, py, pz, dx, dy, dz);
        }
    }

    protected void calculateBlockExplosion() {
        byte maxSize = 16;
        Set<BlockPos> set = Sets.newHashSet();
        for (int j = 0; j < maxSize; ++j) {
            for (int k = 0; k < maxSize; ++k) {
                for (int l = 0; l < maxSize; ++l) {
                    if (j == 0 || j == maxSize - 1 || k == 0 || k == maxSize - 1 || l == 0 || l == maxSize - 1) {
                        double rx = j / 15.0f * 2.0f - 1.0f;
                        double ry = k / 15.0f * 2.0f - 1.0f;
                        double rz = l / 15.0f * 2.0f - 1.0f;
                        double rd = Math.sqrt(rx * rx + ry * ry + rz * rz);
                        rx /= rd;
                        ry /= rd;
                        rz /= rd;
                        float strength = power * (0.7f + world.random.nextFloat() * 0.6f);
                        double dx = x;
                        double dy = y;
                        double dz = z;
                        float f = 0.3f;
                        while (strength > 0.0f) {
                            int x = MathHelper.floor(dx);
                            int y = MathHelper.floor(dy);
                            int z = MathHelper.floor(dz);
                            BlockState blockState = world.getBlockState(x, y, z);
                            if (blockState != States.AIR && blockState.getBlock() != null) {
                                strength -= (blockState.getBlock().getBlastResistance(source) + 0.3f) * f;
                            }
                            if (strength > 0.0f) {
                                set.add(new BlockPos(x, y, z));
                            }
                            dx += rx * 0.3;
                            dy += ry * 0.3;
                            dz += rz * 0.3;
                            strength -= 0.22500001f;
                        }
                    }
                }
            }
        }
        damagedBlocks.addAll(set);
        calculatedExplosion = true;
    }
}
