package net.danygames2014.balkonsweapons.mixin.hold;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.InteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    public ClientPlayerEntity player;

    @Shadow
    public InteractionManager interactionManager;

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButtonState()Z", ordinal = 2))
    boolean handleStopUsingItem(Operation<Boolean> original){
        boolean pressed = original.call();
        if(player.isUsingItem()) {
            if(!pressed) {
                interactionManager.stopUsingItem(player);
            }
            return false;
        }
        return pressed;
    }
}
