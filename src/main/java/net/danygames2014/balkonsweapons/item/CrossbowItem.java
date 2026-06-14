package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.entity.projectile.CrossbowBoltEntity;
import net.danygames2014.balkonsweapons.util.ItemUtil;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class CrossbowItem extends RangedItem{
    public CrossbowItem(Identifier identifier) {
        super(identifier, RangedSpecs.CROSSBOW);
    }

    @Override
    public void effectReloadDone(ItemStack stack, World world, LivingEntity player) {
//        entityliving.swingItem();
        SoundHelper.playSound(world, player.x, player.y, player.z, "random.click", 0.8F, 1.0F / (random.nextFloat() * 0.4F + 0.4F));
    }

    public void resetReload(World world, ItemStack itemstack) {
        setReloadState(itemstack, ReloadState.STATE_NONE);
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
            CrossbowBoltEntity boltEntity = new CrossbowBoltEntity(world, entity);
            boltEntity.setAim(entity, entity.pitch, entity.yaw, 0.0f, 5.0f, 1.5f / f);
            world.spawnEntity(boltEntity);
        }
        int damage = 1;
        if (stack.getDamage() + damage < stack.getMaxDamage()) {
            resetReload(world, stack);
        }
        if (entity instanceof PlayerEntity player) {
            ItemUtil.damageItem(stack, damage, player);
        }
        postShootingEffects(stack, entity, world);
        resetReload(world, stack);
    }

    @Override
    public void effectPlayer(ItemStack stack, PlayerEntity player, World world) {
        player.pitch -= (player.isSneaking() ? 4.0f : 8.0f);
    }

    @Override
    public void effectShoot(World world, double x, double y, double z, float yaw, float pitch) {
        SoundHelper.playSound(world, x, y, z, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
    }
}
