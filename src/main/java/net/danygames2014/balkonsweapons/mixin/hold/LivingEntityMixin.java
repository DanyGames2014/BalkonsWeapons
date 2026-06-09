package net.danygames2014.balkonsweapons.mixin.hold;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Shadow
    protected float sidewaysSpeed;

    @Shadow
    protected float forwardSpeed;

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isSubmergedInWater()Z"))
    void adjustUsingSpeed(CallbackInfo ci) {

        if((LivingEntity) (Object) this instanceof PlayerEntity player && player.isUsingItem()) {
            UseAction action = player.getItemInUse().getUseAction();
            sidewaysSpeed *= action.movementSpeedMultiplier();
            forwardSpeed *= action.movementSpeedMultiplier();
        }
    }
}
