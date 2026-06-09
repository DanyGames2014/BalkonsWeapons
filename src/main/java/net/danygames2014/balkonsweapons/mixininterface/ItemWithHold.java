package net.danygames2014.balkonsweapons.mixininterface;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.api.UseActions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Util;

public interface ItemWithHold {
    int getMaxUseDuration();

    default boolean attemptHold(ItemStack stack, World world, PlayerEntity player) {
        if(getUseAction() != UseActions.NONE) {
            player.setItemInUse(stack, getMaxUseDuration());
            return true;
        }
        return false;
    }

    void startUsing(ItemStack stack, World world, PlayerEntity player);
    boolean usingTick(ItemStack stack, World world, PlayerEntity player, int time);
    boolean stopUsing(ItemStack stack, World world, PlayerEntity player, int time);
    UseAction getUseAction();
}
