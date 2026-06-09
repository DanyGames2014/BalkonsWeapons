package net.danygames2014.balkonsweapons.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.modificationstation.stationapi.api.util.Identifier;

public class BowUseAction extends UseAction{
    public BowUseAction(Identifier identifier) {
        super(identifier);
    }

    @Override
    public void updateInUse(ItemStack stack, PlayerEntity player, int time, boolean finished) {

    }

    @Environment(EnvType.CLIENT)
    @Override
    public void setAngles(BipedEntityModel model, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        float var8 = 0.0F;
        float var9 = 0.0F;
        model.rightArm.roll = 0.0F;
        model.leftArm.roll = 0.0F;
        model.rightArm.yaw = -(0.1F - var8 * 0.6F) + model.head.yaw;
        model.leftArm.yaw = 0.1F - var8 * 0.6F + model.head.yaw + 0.4F;
        model.rightArm.pitch = -((float)Math.PI / 2F) + model.head.pitch;
        model.leftArm.pitch = -((float)Math.PI / 2F) + model.head.pitch;
        model.rightArm.pitch -= var8 * 1.2F - var9 * 0.4F;
        model.leftArm.pitch -= var8 * 1.2F - var9 * 0.4F;
        model.rightArm.roll += MathHelper.cos(animationProgress * 0.09F) * 0.05F + 0.05F;
        model.leftArm.roll -= MathHelper.cos(animationProgress * 0.09F) * 0.05F + 0.05F;
        model.rightArm.pitch += MathHelper.sin(animationProgress * 0.067F) * 0.05F;
        model.leftArm.pitch -= MathHelper.sin(animationProgress * 0.067F) * 0.05F;
    }
}
