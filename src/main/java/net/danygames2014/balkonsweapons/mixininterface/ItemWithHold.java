package net.danygames2014.balkonsweapons.mixininterface;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Util;

public interface ItemWithHold {
    default int getMaxUseDuration() {
        return Util.assertImpl();
    }

    default boolean attemptHold(ItemStack stack, World world, PlayerEntity player) {
        return Util.assertImpl();
    }

    default void startUsing(ItemStack stack, World world, PlayerEntity player) {

    }
    default boolean usingTick(ItemStack stack, World world, PlayerEntity player, int time) {
        return Util.assertImpl();
    }
    default boolean stopUsing(ItemStack stack, World world, PlayerEntity player, int time) {
        return Util.assertImpl();
    }
    default UseAction getUseAction() {
        return Util.assertImpl();
    }
}
