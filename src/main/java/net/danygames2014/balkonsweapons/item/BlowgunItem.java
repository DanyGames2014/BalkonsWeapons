package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.balkonsweapons.entity.projectile.BlowgunDartEntity;
import net.danygames2014.balkonsweapons.util.ItemUtil;
import net.danygames2014.nyalib.particle.ParticleHelper;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class BlowgunItem extends RangedItem{
    public BlowgunItem(Identifier identifier) {
        super(identifier, RangedSpecs.BLOWGUN);
    }

    @Override
    public void effectReloadDone(ItemStack stack, World world, LivingEntity player) {
//        entityliving.swingItem();
        SoundHelper.playSound(world, player.x, player.y, player.z, "random.click", 0.8F, 1.0F / (random.nextFloat() * 0.4F + 0.4F));
    }

    @Override
    public void fire(ItemStack stack, World world, LivingEntity entity, int i) {
        if (!(entity instanceof PlayerEntity player)) return;
        int j = getMaxUseDuration(stack, world, player) - player.getItemInUseDuration();
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f < 0.1f) {
            return;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        ItemStack dartstack = findAmmo(player);
        if (dartstack == null || !(dartstack.getItem() instanceof BlowgunDartItem)) {
            if (false) { // creative check
                dartstack = new ItemStack(BalkonsWeapons.blowgunDart);
            } else {
                return;
            }
        }
        ItemStack dartStackCopy = dartstack.copy();
        dartStackCopy.count = 1;
        if (true) { //creative and enchant check
            consumeAmmo(player);
        }
        if (!world.isRemote) {
            BlowgunDartEntity blowgunDartEntity = new BlowgunDartEntity(world, player, dartStackCopy);
            blowgunDartEntity.setAim(player, player.pitch, player.yaw, 0.0f, f * 1.5f, 1.0f);
            world.spawnEntity(blowgunDartEntity);
        }
        int damage = 1;
        if (stack.getDamage() + damage < stack.getMaxDamage()) {
            setReloadState(stack, ReloadState.STATE_NONE);
        }
        ItemUtil.damageItem(stack, damage, player);
        postShootingEffects(stack, player, world);
        setReloadState(stack, ReloadState.STATE_NONE);
    }

    @Override
    public boolean hasAmmoAndConsume(ItemStack itemstack, World world, LivingEntity livingEntity) {
        if (!(livingEntity instanceof PlayerEntity player)) return false;
        return hasAmmo(itemstack, world, player);
    }

    @Override
    public void soundEmpty(ItemStack itemstack, World world, PlayerEntity player) {
        SoundHelper.playSound(player, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.2F + 0.5F));
    }

    @Override
    public void soundCharge(ItemStack itemstack, World world, PlayerEntity player) {
        SoundHelper.playSound(player, "random.breath", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
    }

    @Override
    public void effectShoot(World world, double x, double y, double z, float yaw, float pitch) {
        SoundHelper.playSound(world, x, y, z, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.2F + 0.5F));
        float particleX = -MathHelper.sin((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        float particleY = -MathHelper.sin(pitch * 0.017453292f) + 1.6f;
        float particleZ = MathHelper.cos((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        ParticleHelper.addParticle(world, "explode", x + particleX, y + particleY, z + particleZ, 0.0, 0.0, 0.0);
    }

    @Override
    public float getMaxZoom() {
        return 0.1F;
    }
}
