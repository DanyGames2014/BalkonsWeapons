package net.danygames2014.balkonsweapons.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.Collections;

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

    public static boolean isItemInList(ItemStack stack, Collection<Item> items) {
        return stack != null && items.contains(stack.getItem());
    }

    public static int findAnyItemSlot(PlayerEntity player, Collection<Item> item) {
        if (isItemInList(player.getHand(), item))
            return player.inventory.selectedSlot;
        for (int i = 0; i < player.inventory.size(); ++i) {
            ItemStack itemstack = player.inventory.getStack(i);
            if (isItemInList(itemstack, item)) return i;
        }
        return -1;
    }

    public static boolean consumeAnyInventoryItem(PlayerEntity player, Collection<Item> item) {
        int slot = findAnyItemSlot(player, item);
        if (slot < 0) return false;
        ItemStack itemStack = player.inventory.main[slot];
        if (--itemStack.count <= 0)
            player.inventory.main[slot] = null;
        return true;
    }

    public static boolean consumeInventoryItem(PlayerEntity player, Item item) {
        return consumeAnyInventoryItem(player, Collections.singletonList(item));
    }
}
