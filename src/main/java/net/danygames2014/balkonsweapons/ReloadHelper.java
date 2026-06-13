package net.danygames2014.balkonsweapons;

import net.danygames2014.balkonsweapons.item.ReloadState;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ReloadHelper {
    @NotNull
    public static ReloadState getReloadState(ItemStack stack) {
        try {
            if (stack.getStationNbt().contains("rld")) {
                return ReloadState.values()[stack.getStationNbt().getByte("rld")];
            }
        } catch (Throwable ignored) {
        }
        return ReloadState.STATE_NONE;
    }

    public static void setReloadState(ItemStack itemstack, ReloadState state) {
        itemstack.getStationNbt().putByte("rld", (byte) state.ordinal());
    }
}
