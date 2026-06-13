package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.entity.projectile.BlunderShotEntity;
import net.danygames2014.balkonsweapons.util.ItemUtil;
import net.danygames2014.nyalib.particle.ParticleHelper;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class BlunderbussItem extends RangedItem{
    public BlunderbussItem(Identifier identifier) {
        super(identifier, RangedSpecs.BLUNDERBUSS);
    }

    @Override
    public void effectReloadDone(ItemStack stack, World world, LivingEntity player) {
//        player.swingItem();
        SoundHelper.playSound(world, player.x, player.y, player.z, "random.door_close", 0.8F, 1.0F / (random.nextFloat() * 0.2F + 0.0F));
    }

    @Override
    public void fire(ItemStack stack, World world, LivingEntity entity, int i) {
        if (!world.isRemote) {
            BlunderShotEntity.fireSpreadShot(world, entity, stack);
        }
        int damage = 1;
        if (stack.getDamage() + damage < stack.getMaxDamage()) {
            setReloadState(stack, ReloadState.STATE_NONE);
        }
        if(entity instanceof PlayerEntity player) {
            ItemUtil.damageItem(stack, damage, player);
        }
        postShootingEffects(stack, entity, world);
    }

    @Override
    public void effectPlayer(ItemStack stack, PlayerEntity player, World world) {
        float f = player.isSneaking() ? -0.1f : -0.2f;
        double d = -MathHelper.sin(player.yaw * 0.017453292f) * MathHelper.cos(0.0f) * f;
        double d2 = MathHelper.cos(player.yaw * 0.017453292f) * MathHelper.cos(0.0f) * f;
        player.pitch -= (player.isSneaking() ? 17.5f : 25.0f);
        player.addVelocity(d, 0.0, d2);
    }

    @Override
    public void effectShoot(World world, double x, double y, double z, float yaw, float pitch) {
        SoundHelper.playSound(world, x, y, z, "random.explode", 3.0F, 1.0F / (random.nextFloat() * 0.4F + 0.6F));
        float particleX = -MathHelper.sin((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        float particleY = -MathHelper.sin(pitch * 0.017453292f) + 1.6f;
        float particleZ = MathHelper.cos((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        for (int i = 0; i < 3; ++i) {
            ParticleHelper.addParticle(world, "smoke", x + particleX, y + particleY, z + particleZ, 0.0, 0.0, 0.0);
        }
        ParticleHelper.addParticle(world, "flame", x + particleX, y + particleY, z + particleZ, 0.0, 0.0, 0.0);
    }

    @Override
    protected float getMaxZoom() {
        return 0.07F;
    }
}
