package net.danygames2014.balkonsweapons.mixininterface;

import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Util;

public interface PlayerEntityWithHold {
    // returns the item that is currently in use
    default ItemStack getItemInUse() {
        return Util.assertImpl();
    }

    default boolean isUsingItem() {
        return Util.assertImpl();
    }

    default int getItemInUseDuration() {
        return Util.assertImpl();
    }

    default void stopUsingItem() {

    }

    default void finishUsingItem() {

    }

    // Play sounds and create particles for use state
    default void updateItemInUse(ItemStack stack, int time, boolean finished) {

    }

    // returns the getItemInUse count, this value is set to getMaxItemUseDuration and then decremented each tick
    default int getItemInUseTime(){
        return Util.assertImpl();
    }

    default void setItemInUse(ItemStack stack, int inUseCount) {

    }

    default void clearItemInUse() {

    }
}
