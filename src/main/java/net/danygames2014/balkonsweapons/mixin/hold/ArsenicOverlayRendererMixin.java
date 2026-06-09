package net.danygames2014.balkonsweapons.mixin.hold;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.danygames2014.balkonsweapons.api.UseAction;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.ArsenicOverlayRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11.glRotatef;

@Mixin(ArsenicOverlayRenderer.class)
public class ArsenicOverlayRendererMixin {
    @WrapOperation(method = "renderVanilla(FFLnet/minecraft/entity/player/ClientPlayerEntity;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal = 0))
    void renderFirstPersonHoldVanilla(float x, float y, float z, Operation<Void> original, float tickDelta, float var2, ClientPlayerEntity player, ItemStack stack) {
        if(player.getItemInUseDuration() > 0) {
            UseAction action = stack.getUseAction(player.world, player);
            action.transformFirstPersonSwingProgress(player, stack, tickDelta);
        } else {
            original.call(x, y, z);
        }
    }

    @Inject(method = "renderVanilla(FFLnet/minecraft/entity/player/ClientPlayerEntity;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glScalef(FFF)V", ordinal = 0))
    void renderFirstPersonHoldVanilla2(float tickDelta, float var2, ClientPlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if(player.getItemInUseDuration() > 0) {
            UseAction action = stack.getUseAction(player.world, player);
            action.transformFirstPerson(player, stack, tickDelta);
        }
    }

    @WrapOperation(method = "renderModel(FFLnet/minecraft/entity/player/ClientPlayerEntity;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal = 0))
    void cancelTransformationModel(float x, float y, float z, Operation<Void> original, float tickDelta, float var2, ClientPlayerEntity player, ItemStack stack){
        if (player.getItemInUseDuration() <= 0) {
            original.call(x, y, z);
        }
    }

    @Inject(method = "renderModel(FFLnet/minecraft/entity/player/ClientPlayerEntity;Lnet/minecraft/item/ItemStack;)V", at = @At("HEAD"))
    void renderFirstPersonHoldModel(float tickDelta, float var2, ClientPlayerEntity player, ItemStack stack, CallbackInfo ci){
        if(player.getItemInUseDuration() > 0) {
            UseAction action = stack.getUseAction(player.world, player);
            action.transformFirstPersonSwingProgress(player, stack, tickDelta);
        }
    }

    @Inject(method = "renderModel(FFLnet/minecraft/entity/player/ClientPlayerEntity;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isHandheldRod()Z", shift = At.Shift.AFTER))
    void renderFirstPersonHoldModel2(float tickDelta, float var2, ClientPlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if(player.getItemInUseDuration() > 0) {
            UseAction action = stack.getUseAction(player.world, player);
            glRotatef(45.0f, 0, 1, 0);
            action.transformFirstPerson(player, stack, tickDelta);
            glRotatef(-45.0f, 0, 1, 0);
        }
    }
}
