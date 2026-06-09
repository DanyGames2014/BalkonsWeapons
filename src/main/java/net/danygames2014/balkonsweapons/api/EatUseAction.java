package net.danygames2014.balkonsweapons.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.modificationstation.stationapi.api.util.Identifier;
import org.lwjgl.opengl.GL11;

public class EatUseAction extends UseAction{
    public EatUseAction(Identifier identifier) {
        super(identifier);
    }

    @Override
    public void updateInUse(ItemStack stack, PlayerEntity player, int time, boolean finished) {

    }

    @Environment(EnvType.CLIENT)
    @Override
    public void setAngles(BipedEntityModel model, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        model.rightArm.pitch = model.rightArm.pitch * 0.5F - ((float)Math.PI / 10F) * 3;
    }

    @Override
    public void renderFirstPersonSwingProgress(PlayerEntity player, ItemStack stack, float tickDelta) {
        float var14 = (float)player.getItemInUseDuration() - tickDelta + 1.0F;
        float var15 = 1.0F - var14 / (float)stack.getMaxUseDuration();
        float var16 = 1.0F - var15;
        var16 = var16 * var16 * var16;
        var16 = var16 * var16 * var16;
        var16 = var16 * var16 * var16;
        float var17 = 1.0F - var16;
        GL11.glTranslatef(0.0F, MathHelper.abs(MathHelper.cos(var14 / 4.0F * (float)Math.PI) * 0.1F) * (float)((double)var15 > 0.2D ? 1 : 0), 0.0F);
        GL11.glTranslatef(var17 * 0.6F, -var17 * 0.5F, 0.0F);
        GL11.glRotatef(var17 * 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(var17 * 10.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(var17 * 30.0F, 0.0F, 0.0F, 1.0F);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void renderFirstPerson(PlayerEntity player, ItemStack stack, float tickDelta) {
    }


}
