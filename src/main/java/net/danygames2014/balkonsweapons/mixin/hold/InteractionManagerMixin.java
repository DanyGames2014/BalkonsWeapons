package net.danygames2014.balkonsweapons.mixin.hold;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.danygames2014.balkonsweapons.mixininterface.InteractionManagerWithHold;
import net.minecraft.client.InteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InteractionManager.class)
public class InteractionManagerMixin implements InteractionManagerWithHold {

    @Override
    public void stopUsingItem(PlayerEntity player) {
        player.stopUsingItem();
    }

    @WrapOperation(method = "interactItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/item/ItemStack;"))
    ItemStack attemptUse(ItemStack instance, World world, PlayerEntity user, Operation<ItemStack> original){
        if(instance.attemptHold(world, user)) {
            return instance;
        } else {
            return original.call(instance, world, user);
        }
    }
}
