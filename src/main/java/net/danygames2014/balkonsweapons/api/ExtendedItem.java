package net.danygames2014.balkonsweapons.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public interface ExtendedItem {
    int getMaxUseDuration();

    void startUsing(ItemStack stack, World world, PlayerEntity player);
    boolean usingTick(ItemStack stack, World world, PlayerEntity player, int time);
    boolean stopUsing(ItemStack stack, World world, PlayerEntity player, int time);
    Identifier getUseAction();
}
