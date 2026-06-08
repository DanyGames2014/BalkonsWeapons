package net.danygames2014.balkonsweapons.mixin.hold;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.mixininterface.ItemStackWithHold;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemStackWithHold {
    @Shadow
    public abstract Item getItem();

    @Override
    public int getMaxUseDuration() {
        return getItem().getMaxUseDuration();
    }

    @Override
    public boolean attemptHold(World world, PlayerEntity player) {
        ItemStack self = (ItemStack) (Object) this;
        return getItem().attemptHold(self, world, player);
    }

    @Override
    public void startUsing(World world, PlayerEntity player) {
        ItemStack self = (ItemStack) (Object) this;
        getItem().startUsing(self, world, player);
    }

    @Override
    public boolean usingTick(World world, PlayerEntity player, int time) {
        ItemStack self = (ItemStack) (Object) this;
        return getItem().usingTick(self, world, player, time);
    }

    @Override
    public boolean stopUsing(World world, PlayerEntity player, int time) {
        ItemStack self = (ItemStack) (Object) this;
        return getItem().stopUsing(self, world, player, time);
    }

    @Override
    public UseAction getUseAction() {
        return getItem().getUseAction();
    }
}
