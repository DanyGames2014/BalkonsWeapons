package net.danygames2014.balkonsweapons.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ExtendedItem {
    int getMaxUseDuration();
    void stopUsing(ItemStack stack, World world, PlayerEntity player, int time);
    Action getUseAction();
}
