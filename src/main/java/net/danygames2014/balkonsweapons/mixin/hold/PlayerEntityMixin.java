package net.danygames2014.balkonsweapons.mixin.hold;

import net.danygames2014.balkonsweapons.mixininterface.PlayerEntityWithHold;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityWithHold {

    @Shadow
    public PlayerInventory inventory;
    @Unique
    private ItemStack itemInUse;

    @Unique
    private int itemInUseCount;

    public PlayerEntityMixin(World world) {
        super(world);
    }

    @Override
    public ItemStack getItemInUse() {
        return this.itemInUse;
    }

    @Override
    public boolean isUsingItem() {
        return this.itemInUse != null;
    }

    @Override
    public int getItemInUseDuration() {
        return this.itemInUseCount;
    }

    @Override
    public void stopUsingItem() {
        if(this.itemInUse != null) {
            PlayerEntity self = (PlayerEntity) (Object) this;
            boolean runUse = this.itemInUse.stopUsing(world, self, this.itemInUseCount);
            if(runUse) {
                int count = this.itemInUse.count;
                ItemStack result = this.itemInUse.use(world, self);

                if(result != this.itemInUse || result != null && result.count != count) {
                    this.inventory.main[this.inventory.selectedSlot] = result;

                    if(result.count == 0) {
                        this.inventory.main[this.inventory.selectedSlot] = null;
                    }
                }
            }
        }
        this.clearItemInUse();
    }

    @Override
    public void updateItemInUse(ItemStack stack, int time, boolean finished) {
        PlayerEntity self = (PlayerEntity) (Object) this;
        stack.getUseAction(self.world, self).updateInUse(stack, self, time, finished);
    }

    @Override
    public int getItemInUseTime() {
        return this.isUsingItem() ? this.itemInUse.getMaxUseDuration() - this.itemInUseCount : 0;
    }

    @Override
    public void clearItemInUse() {
        this.itemInUse = null;
        this.itemInUseCount = 0;
    }

    @Override
    public void finishUsingItem() {
        PlayerEntity self = (PlayerEntity) (Object) this;
        if(this.itemInUse != null) {
            this.updateItemInUse(this.itemInUse, itemInUseCount, true);
            int count = this.itemInUse.count;
            ItemStack result = this.itemInUse.use(world, self);

            if(result != this.itemInUse || result != null && result.count != count) {
                this.inventory.main[this.inventory.selectedSlot] = result;

                if(result.count == 0) {
                    this.inventory.main[this.inventory.selectedSlot] = null;
                }
            }

            this.clearItemInUse();
        }
    }

    @Override
    public void setItemInUse(ItemStack stack, int inUseCount) {
        PlayerEntity self = (PlayerEntity) (Object) this;
        if(stack != this.itemInUse) {
            this.itemInUse = stack;
            this.itemInUseCount = inUseCount;
            stack.startUsing(world, self);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void useItemTick(CallbackInfo ci){
        PlayerEntity self = (PlayerEntity) (Object) this;
        if(this.itemInUse != null) {
            ItemStack stack = this.inventory.getSelectedItem();
            if(stack == this.itemInUse) {
                if(--this.itemInUseCount == 0 && !world.isRemote) {
                    this.finishUsingItem();
                }
                if(this.itemInUse != null) {
                    stack.usingTick(world, self, getItemInUseTime());
                    this.updateItemInUse(stack, itemInUseCount, false);
                }
            }
        } else {
            clearItemInUse();
        }
    }
}
