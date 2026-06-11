package net.danygames2014.balkonsweapons.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ItemUtil {
    public static void decreaseStackCount(ItemStack stack, int amount, PlayerEntity player) {
        stack.count -= amount;
        if (stack.count <= 0) {
            player.inventory.main[player.inventory.selectedSlot] = null;
        }
    }

    public static void damageItem(ItemStack stack, int damage, PlayerEntity player) {
        stack.damage(damage, player);
        if (stack.count <= 0) {
            player.inventory.main[player.inventory.selectedSlot] = null;
        }
    }
}
