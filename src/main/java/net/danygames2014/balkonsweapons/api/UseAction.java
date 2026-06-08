package net.danygames2014.balkonsweapons.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class UseAction {
    private final Identifier identifier;
    public final Random random = new Random();

    public UseAction(Identifier identifier) {
        this.identifier = identifier;
    }

    public void updateInUse(ItemStack stack, PlayerEntity player, int particleCount) {

    }
}
