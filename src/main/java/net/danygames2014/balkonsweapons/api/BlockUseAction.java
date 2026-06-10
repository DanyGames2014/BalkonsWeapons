package net.danygames2014.balkonsweapons.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class BlockUseAction extends UseAction{
    public BlockUseAction(Identifier identifier) {
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
    public void transformFirstPersonSwingProgressVanilla(PlayerEntity player, ItemStack stack, float tickDelta) {

    }

    @Environment(EnvType.CLIENT)
    @Override
    public void transformFirstPersonVanilla(PlayerEntity player, ItemStack stack, float tickDelta) {
        GL11.glTranslatef(-0.2F, 0.08F, -0.0F);
        GL11.glRotatef(30.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-80.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public void transformThirdPersonVanilla(PlayerEntity player, ItemStack stack, float tickDelta) {
        GL11.glTranslatef(0.05F, 0.0F, 0.3F);
        GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
    }

    @Override
    public void transformThirdPersonModel(PlayerEntity player, ItemStack stack, float tickDelta) {
        GL11.glTranslatef(-0.2340F, -0.2040F, -0.1830F);
        GL11.glScalef(1.1f, 1.1f, 1.1f);
        GL11.glRotatef(6.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-20.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(-00.0F, 0.0F, 0.0F, 1.0F);
    }
}
