package net.danygames2014.balkonsweapons.mixin.hold;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.api.UseActions;
import net.danygames2014.balkonsweapons.mixininterface.ItemStackWithHold;
import net.danygames2014.balkonsweapons.mixininterface.ItemWithHold;
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
        if(getItem() instanceof ItemWithHold itemWithHold){
            return itemWithHold.getMaxUseDuration();
        }
        return 0;
    }

    @Override
    public boolean attemptHold(World world, PlayerEntity player) {
        ItemStack self = (ItemStack) (Object) this;
        if(getItem() instanceof ItemWithHold itemWithHold){
            return itemWithHold.attemptHold(self, world, player);
        }
        return false;
    }

    @Override
    public void startUsing(World world, PlayerEntity player) {
        ItemStack self = (ItemStack) (Object) this;
        if(getItem() instanceof ItemWithHold itemWithHold){
            itemWithHold.startUsing(self, world, player);
        }
    }

    @Override
    public void usingTick(World world, PlayerEntity player, int time) {
        ItemStack self = (ItemStack) (Object) this;
        if(getItem() instanceof ItemWithHold itemWithHold){
            itemWithHold.usingTick(self, world, player, time);
        }
    }

    @Override
    public boolean stopUsing(World world, PlayerEntity player, int time) {
        ItemStack self = (ItemStack) (Object) this;
        if(getItem() instanceof ItemWithHold itemWithHold){
            return itemWithHold.stopUsing(self, world, player, time);
        }
        return false;
    }

    @Override
    public UseAction getUseAction(World world, PlayerEntity player) {
        ItemStack self = (ItemStack) (Object) this;
        if(getItem() instanceof ItemWithHold itemWithHold){
            return itemWithHold.getUseAction(self, world, player);
        }
        return UseActions.NONE;
    }
}
