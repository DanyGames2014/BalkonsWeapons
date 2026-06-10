package net.danygames2014.balkonsweapons.util;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.DataTrackerEntry;
import net.minecraft.item.ItemStack;

public class DataTrackerUtil {
    public static ItemStack getItemStack(DataTracker tracker, int id) {
        return (ItemStack) ((DataTrackerEntry)tracker.entries.get(id)).get();
    }

    public static Float getFloat(DataTracker tracker, int id) {
        return (Float) ((DataTrackerEntry)tracker.entries.get(id)).get();
    }
}
