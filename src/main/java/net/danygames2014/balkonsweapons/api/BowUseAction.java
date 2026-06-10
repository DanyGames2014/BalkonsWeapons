package net.danygames2014.balkonsweapons.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.modificationstation.stationapi.api.util.Identifier;
import org.lwjgl.opengl.GL11;

public class BowUseAction extends UseAction{

    public BowUseAction(Identifier identifier) {
        super(identifier);
    }

    @Override
    public void updateInUse(ItemStack stack, PlayerEntity player, int time, boolean finished) {

    }

    @Environment(EnvType.CLIENT)
    @Override
    public float getFovMultiplier(float tickDelta, int time) {
        float f = time / 20.0F;
        float fNext = (time + 1) / 20.0F;

        if(f > 1.0F) {
            f = 1.0F;
        } else {
            f *= f;
        }

        if(fNext > 1.0F) {
            fNext = 1.0F;
        } else {
            fNext *= fNext;
        }



        float fov = 1.0F - f * 0.15F;
        float fovNext = 1.0F - fNext * 0.15F;

        return net.modificationstation.stationapi.api.util.math.MathHelper.lerp(tickDelta, fov, fovNext);
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

    @Environment(EnvType.CLIENT)
    @Override
    public void transformFirstPersonVanilla(PlayerEntity player, ItemStack stack, float tickDelta) {
        float var20 = 0;
        GL11.glRotatef(-18.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-12.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-8.0F, 1.0F, 0.0F, 0.0F);

        GL11.glTranslatef(-0.2F, 0.2F, 0.0F);
        float var18 = (float)stack.getMaxUseDuration(player.world, player) - ((float)player.getItemInUseDuration() - tickDelta + 1.0F);
        float var19 = var18 / 20.0F;
        var19 = (var19 * var19 + var19 * 2.0F) / 3.0F;

        if (var19 > 1.0F)
        {
            var19 = 1.0F;
        }

        if (var19 > 0.1F)
        {
            GL11.glTranslatef(0.0F, MathHelper.sin((var18 - 0.1F) * 1.3F) * 0.01F * (var19 - 0.1F), 0.0F);
        }

        GL11.glTranslatef(0.0F, 0.0F, var19 * 0.1F);
        GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, 0.5F, 0.0F);
        var20 = 1.0F + var19 * 0.2F;
        GL11.glScalef(1.0F, 1.0F, var20);
        GL11.glTranslatef(0.0F, -0.5F, 0.0F);
        GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void transformFirstPersonModel(PlayerEntity player, ItemStack stack, float tickDelta) {
        GL11.glRotatef(45.0f, 0, 1, 0);
        transformFirstPersonVanilla(player, stack, tickDelta);
        GL11.glRotatef(-45.0f, 0, 1, 0);
    }
}
