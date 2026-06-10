package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.api.UseActions;
import net.danygames2014.balkonsweapons.entity.projectile.BoomerangEntity;
import net.danygames2014.balkonsweapons.item.component.WeaponItem;
import net.danygames2014.balkonsweapons.mixininterface.ItemWithHold;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class BoomerangItem extends TemplateItem implements ItemWithHold, WeaponItem {
    public BoomerangItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public int getMaxUseDuration(ItemStack stack, World world, PlayerEntity player) {
        return 72000;
    }

    @Override
    public void startUsing(ItemStack stack, World world, PlayerEntity player) {

    }

    @Override
    public void usingTick(ItemStack stack, World world, PlayerEntity player, int time) {

    }

    @Override
    public boolean stopUsing(ItemStack stack, World world, PlayerEntity player, int time) {
        if(stack == null) {
            return false;
        }

        int j = getMaxUseDuration(stack, world, player) - player.getItemInUseDuration();

        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f < 0.1f) {
            return false;
        }
        boolean crit = false;
        if (f > 1.5f) {
            f = 1.5f;
            crit = true;
        }
        f *= 1.5f;
        if (!world.isRemote) {
            BoomerangEntity boomerang = new BoomerangEntity(world, player, stack.copy());
            boomerang.setAim(player, player.pitch, player.yaw, 0.0f, f, 5.0f);
            boomerang.setIsCritical(crit);
            world.spawnEntity(boomerang);
        }
        SoundHelper.playSound(player, "random.bow", 0.6F,
                1.0F / (getItemRandom().nextFloat() * 0.4F + 1.0F));
//        if (!(entityliving instanceof EntityPlayer) || !((EntityPlayer) entityliving).capabilities.isCreativeMode) {
//            WMItem.decrStackSize(itemstack, 1, entityliving);
//        }

        return false;
    }

    @Override
    public UseAction getUseAction(ItemStack stack, World world, PlayerEntity player) {
        return UseActions.BLOCK;
    }

    @Override
    public Random getItemRandom() {
        return random;
    }

    @Override
    public ToolMaterial getToolMaterial() {
        return ToolMaterial.DIAMOND;
    }

    @Override
    public int getEntityDamage() {
        return 5;
    }
}
