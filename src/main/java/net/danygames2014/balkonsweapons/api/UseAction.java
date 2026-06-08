package net.danygames2014.balkonsweapons.api;

import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class UseAction {
    private final Identifier identifier;

    public UseAction(Identifier identifier) {
        this.identifier = identifier;
    }

    public void updateInUse(ItemStack stack) {

    }
}
