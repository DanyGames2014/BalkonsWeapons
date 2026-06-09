package net.danygames2014.balkonsweapons.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class NoneUseAction extends UseAction{
    public NoneUseAction(Identifier identifier) {
        super(identifier);
    }

    @Override
    public void updateInUse(ItemStack stack, PlayerEntity player, int time, boolean finished) {

    }

    @Environment(EnvType.CLIENT)
    @Override
    public void setAngles(BipedEntityModel model, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {

    }
}
