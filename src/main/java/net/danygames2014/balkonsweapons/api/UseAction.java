package net.danygames2014.balkonsweapons.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

public abstract class UseAction {
    private final Identifier identifier;
    public final Random random = new Random();

    public UseAction(Identifier identifier) {
        this.identifier = identifier;
    }

    public abstract void updateInUse(ItemStack stack, PlayerEntity player, int time, boolean finished);

    @Environment(EnvType.CLIENT)
    public abstract void setAngles(BipedEntityModel model, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale);

    @Environment(EnvType.CLIENT)
    public abstract void renderFirstPersonSwingProgress(PlayerEntity player, ItemStack stack, float tickDelta);
    public abstract void renderFirstPerson(PlayerEntity player, ItemStack stack, float tickDelta);
    public float movementSpeedMultiplier() {
        return 0.2F;
    }
}
