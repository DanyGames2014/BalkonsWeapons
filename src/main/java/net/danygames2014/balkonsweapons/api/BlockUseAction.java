package net.danygames2014.balkonsweapons.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;
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
    public void renderFirstPersonSwingProgress(PlayerEntity player, ItemStack stack, float tickDelta) {

    }

    @Environment(EnvType.CLIENT)
    @Override
    public void renderFirstPerson(PlayerEntity player, ItemStack stack, float tickDelta) {
        GL11.glTranslatef(-0.2F, 0.08F, -0.0F);
        GL11.glRotatef(30.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-80.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
    }


}
