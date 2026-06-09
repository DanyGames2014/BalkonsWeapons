package net.danygames2014.balkonsweapons.mixin.hold;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    private Minecraft client;

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    void handleUseActionFov(float tickDelta, CallbackInfoReturnable<Float> cir){
        if(client.player.isUsingItem()) {
            float fov = cir.getReturnValueF() * client.player.getItemInUse().getUseAction(client.player.world, client.player).getFovMultiplier(tickDelta, client.player.getItemInUseTime());
            cir.setReturnValue(fov);
        }
    }
}
