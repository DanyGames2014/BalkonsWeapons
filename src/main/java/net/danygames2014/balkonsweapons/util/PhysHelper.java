package net.danygames2014.balkonsweapons.util;

import net.danygames2014.balkonsweapons.world.explosion.AdvancedExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class PhysHelper {
    private static double kbMotionX = 0.0;
    private static double kbMotionY = 0.0;
    private static double kbMotionZ = 0.0;
    private static int knockBackModifier = 0;

    public static AdvancedExplosion createStandardExplosion(World world, Entity source, double x, double y, double z, float power, boolean flame, boolean smoke) {
        AdvancedExplosion explosion = new AdvancedExplosion(world, source, x, y, z, power, flame, smoke);
        explosion.entityExplosion();
        explosion.blockExplosion();
        explosion.addParticles(true, true);
//        sendExplosion(world, explosion, true, true); // might not need this?
        return explosion;
    }

    public static AdvancedExplosion createAdvancedExplosion(World world, Entity source, double x, double y, double z, float power, boolean destroyBlocks, boolean spawnSmallParticles, boolean spawnBigParticles, boolean flame, boolean smoke) {
        AdvancedExplosion explosion = new AdvancedExplosion(world, source, x, y, z, power, flame, smoke);
        explosion.entityExplosion();
        if(destroyBlocks) {
            explosion.blockExplosion();
        }
        if(flame) {
            explosion.addFlames();
        }
        explosion.addParticles(spawnSmallParticles, spawnBigParticles);
        //        sendExplosion(world, explosion, true, true); // might not need this?
        return explosion;
    }

    public static AdvancedExplosion createAdvancedExplosion(World world, Entity source, double x, double y, double z, float power, boolean destroyBlocks, boolean spawnParticles, boolean flame, boolean smoke) {
        AdvancedExplosion explosion = new AdvancedExplosion(world, source, x, y, z, power, flame, smoke);
        explosion.entityExplosion();
        if(destroyBlocks) {
            explosion.blockExplosion();
        }
        if(flame) {
            explosion.addFlames();
        }
        explosion.addParticles(spawnParticles, spawnParticles);
        //        sendExplosion(world, explosion, true, true); // might not need this?
        return explosion;
    }
}
