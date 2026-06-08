package net.danygames2014.balkonsweapons.mixininterface;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Util;

public interface ItemStackWithHold {
    default int getMaxUseDuration() {
        return Util.assertImpl();
    }

    default boolean attemptHold(World world, PlayerEntity player) {
        return Util.assertImpl();
    }

    default void startUsing(World world, PlayerEntity player) {

    }
    default boolean usingTick(World world, PlayerEntity player, int time) {
        return Util.assertImpl();
    }
    default boolean stopUsing(World world, PlayerEntity player, int time) {
        return Util.assertImpl();
    }
    default UseAction getUseAction() {
        return Util.assertImpl();
    }
}
