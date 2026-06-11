package net.danygames2014.balkonsweapons.util;

import net.danygames2014.balkonsweapons.world.explosion.AdvancedExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PhysHelper {
    private static double kbVelocityX = 0.0;
    private static double kbVelocityY = 0.0;
    private static double kbVelocityZ = 0.0;
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

    public static void knockback(LivingEntity target, LivingEntity attacker, float knockback) {
        target.velocityX = kbVelocityX;
        target.velocityY = kbVelocityY;
        target.velocityZ = kbVelocityZ;
        double dx = attacker.x - target.x;
        double dz;
        for (dz = attacker.z - target.z; dx * dx + dz * dz < 1E-4D;
             dz = (Math.random() - Math.random()) * 0.01D) {
            dx = (Math.random() - Math.random()) * 0.01D;
        }
        target.tilt =
                (float) (Math.atan2(dz, dx) * 180.0 / 3.141592653589793) - target.yaw;
        float f = MathHelper.sqrt(dx * dx + dz * dz);
        target.velocityX -= dx / f * knockback;
        target.velocityY += knockback;
        target.velocityZ -= dz / f * knockback;
        if (target.velocityY > 0.4) {
            target.velocityY = 0.4;
        }
        if (knockBackModifier > 0) {
            dx = -Math.sin(Math.toRadians(attacker.yaw)) * knockBackModifier * 0.5;
            dz = Math.cos(Math.toRadians(attacker.yaw)) * knockBackModifier * 0.5;
            target.addVelocity(dx, 0.1, dz);
        }
//        if (target instanceof EntityPlayerMP) {
//            ((EntityPlayerMP) target).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(target));
//        }
        knockBackModifier = 0;
        kbVelocityX = (kbVelocityY = (kbVelocityZ = 0.0));
    }

    public static void prepareKnockbackOnEntity(LivingEntity attacker, LivingEntity target) {
        kbVelocityX = target.velocityX;
        kbVelocityY = target.velocityY;
        kbVelocityZ = target.velocityZ;
    }
}
