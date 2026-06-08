package net.danygames2014.balkonsweapons.mixin.hold;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.api.UseActions;
import net.danygames2014.balkonsweapons.mixininterface.ItemWithHold;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemWithHold {

    @Shadow
    public abstract int getMaxCount();

    @Override
    public int getMaxUseDuration() {
        return 0;
    }

    @Override
    public boolean attemptHold(ItemStack stack, World world, PlayerEntity player) {

        if(getUseAction() != UseActions.NONE) {
            player.setItemInUse(stack, getMaxUseDuration());
            return true;
        }
        return false;
    }

    @Override
    public void startUsing(ItemStack stack, World world, PlayerEntity player) {

    }

    @Override
    public boolean usingTick(ItemStack stack, World world, PlayerEntity player, int time) {
        return false;
    }

    @Override
    public boolean stopUsing(ItemStack stack, World world, PlayerEntity player, int time) {
        return false;
    }

    @Override
    public UseAction getUseAction() {
        return UseActions.NONE;
    }
}
