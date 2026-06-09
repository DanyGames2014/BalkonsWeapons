package net.danygames2014.balkonsweapons.mixin.hold;

import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Shadow
    private BipedEntityModel bipedModel;

    @Shadow
    private BipedEntityModel armor1;

    @Shadow
    private BipedEntityModel armor2;

    @Inject(method = "render(Lnet/minecraft/entity/player/PlayerEntity;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSneaking()Z", ordinal = 0))
    void setPlayerModelUseAction(PlayerEntity playerEntity, double d, double e, double f, float g, float h, CallbackInfo ci){
        if(playerEntity.isUsingItem()) {
            bipedModel.setUseAction(playerEntity.getItemInUse().getUseAction(playerEntity.world, playerEntity));
            armor1.setUseAction(playerEntity.getItemInUse().getUseAction(playerEntity.world, playerEntity));
            armor2.setUseAction(playerEntity.getItemInUse().getUseAction(playerEntity.world, playerEntity));
        }
    }

    @Inject(method = "render(Lnet/minecraft/entity/player/PlayerEntity;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;render(Lnet/minecraft/entity/LivingEntity;DDDFF)V", shift = At.Shift.AFTER))
    void cleanupPlayerModelUseAction(PlayerEntity playerEntity, double d, double e, double f, float g, float h, CallbackInfo ci) {
        bipedModel.setUseAction(null);
        armor1.setUseAction(null);
        armor2.setUseAction(null);
    }
}
