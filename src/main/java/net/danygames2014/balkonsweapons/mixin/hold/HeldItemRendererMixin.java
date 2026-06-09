package net.danygames2014.balkonsweapons.mixin.hold;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.danygames2014.balkonsweapons.api.UseAction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Shadow
    private Minecraft minecraft;

    @Shadow
    private ItemStack stack;

//    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal = 4))
//    void renderFirstPersonHold(float x, float y, float z, Operation<Void> original, float tickDelta) {
//        PlayerEntity player = minecraft.player;
//        if(player.getItemInUseDuration() > 0) {
//            UseAction action = stack.getUseAction();
//            action.renderFirstPersonSwingProgress(player, stack, tickDelta);
//        } else {
//            original.call(x, y, z);
//        }
//    }
//
//    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glScalef(FFF)V", ordinal = 3))
//    void renderFirstPersonHold2(float tickDelta, CallbackInfo ci) {
//        PlayerEntity player = minecraft.player;
//        if(player.getItemInUseDuration() > 0) {
//            UseAction action = stack.getUseAction();
//            action.renderFirstPersonSwingProgress(player, stack, tickDelta);
//        }
//    }
}
