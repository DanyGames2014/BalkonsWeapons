package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.entity.projectile.MortarShellEntity;
import net.danygames2014.balkonsweapons.util.ItemUtil;
import net.danygames2014.nyalib.particle.ParticleHelper;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class MortarItem extends RangedItem{
    public MortarItem(Identifier identifier) {
        super(identifier, RangedSpecs.MORTAR);
    }

    @Override
    public void effectReloadDone(ItemStack stack, World world, LivingEntity player) {
//        entityliving.swingItem();
        SoundHelper.playSound(world, player.x, player.y, player.z, "random.door_close", 0.8F,
                1.0F / (random.nextFloat() * 0.2F + 0.4F));
    }

    @Override
    public void fire(ItemStack stack, World world, LivingEntity entity, int i) {
        int j = MAX_DELAY;
        if(entity instanceof PlayerEntity player) {
            j = getMaxUseDuration(stack, world, player) - player.getItemInUseDuration();
        }
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f > 1.0f) {
            f = 1.0f;
        }
        f += 0.02f;
        if (!world.isRemote) {
            MortarShellEntity mortarShellEntity = new MortarShellEntity(world, entity);
            mortarShellEntity.setAim(entity, entity.pitch, entity.yaw, 0.0f, 1.4f,
                    1.0f / f);
            world.spawnEntity(mortarShellEntity);
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
        float f = player.isSneaking() ? -0.15f : -0.25f;
        double d = -MathHelper.sin(player.yaw * 0.017453292f) * MathHelper.cos(0.0f) * f;
        double d2 = MathHelper.cos(player.yaw * 0.017453292f) * MathHelper.cos(0.0f) * f;
        player.pitch -= (player.isSneaking() ? 20.0f : 30.0f);
        player.addVelocity(d, 0.0, d2);
    }

    @Override
    public void effectShoot(World world, double x, double y, double z, float yaw, float pitch) {
        SoundHelper.playSound(world, x, y, z, "random.explode", 3F, 1F / (random.nextFloat() * 0.2F + 0.2F));
        float particleX = -MathHelper.sin((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        float particleY = -MathHelper.sin(pitch * 0.017453292f) + 1.6f;
        float particleZ = MathHelper.cos((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        for (int i = 0; i < 3; ++i) {
            ParticleHelper.addParticle(world, "smoke", x + particleX, y + particleY, z + particleZ, 0.0, 0.0,
                    0.0);
        }
        ParticleHelper.addParticle(world,"flame", x + particleX, y + particleY, z + particleZ, 0.0, 0.0, 0.0);
    }

    @Override
    public float getMaxZoom() {
        return 0.03F;
    }
}
