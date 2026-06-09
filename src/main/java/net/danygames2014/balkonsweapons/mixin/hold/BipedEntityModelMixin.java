package net.danygames2014.balkonsweapons.mixin.hold;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.mixininterface.BipedEntityModelWithHold;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin implements BipedEntityModelWithHold {

    @Unique
    UseAction useAction;

    @Override
    public void setUseAction(UseAction useAction) {
        this.useAction = useAction;
    }

    @Inject(method = "setAngles", at = @At("TAIL"))
    void setUseActionAngles(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale, CallbackInfo ci){
        if(useAction != null) {
            BipedEntityModel self = (BipedEntityModel) (Object) this;
            useAction.setAngles(self, limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
        }
    }
}
