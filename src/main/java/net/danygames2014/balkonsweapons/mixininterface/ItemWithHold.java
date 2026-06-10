package net.danygames2014.balkonsweapons.mixininterface;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.api.UseActions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ItemWithHold {
    int getMaxUseDuration(ItemStack stack, World world, PlayerEntity player);

    default boolean attemptHold(ItemStack stack, World world, PlayerEntity player) {
        if(getUseAction(stack, world, player) != UseActions.NONE) {
            player.setItemInUse(stack, getMaxUseDuration(stack, world, player));
            return true;
        }
        return false;
    }

    void startUsing(ItemStack stack, World world, PlayerEntity player);
    void usingTick(ItemStack stack, World world, PlayerEntity player, int time);
    boolean stopUsing(ItemStack stack, World world, PlayerEntity player, int time);
    UseAction getUseAction(ItemStack stack, World world, PlayerEntity player);
}
