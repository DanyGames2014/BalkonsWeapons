package net.danygames2014.balkonsweapons.client.render.entity;

import net.danygames2014.balkonsweapons.entity.projectile.BoomerangEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.StationRenderAPI;
import net.modificationstation.stationapi.api.client.render.RendererAccess;
import net.modificationstation.stationapi.api.client.render.model.BakedModelRenderer;
import net.modificationstation.stationapi.api.client.render.model.json.ModelTransformation;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class BoomerangEntityRenderer extends EntityRenderer {
    @Override
    public void render(Entity entity, double x, double y, double z, float yaw, float pitch) {
        BoomerangEntity boomerang = (BoomerangEntity) entity;
        GL11.glPushMatrix();
        StationRenderAPI.getBakedModelManager().getAtlas(Atlases.GAME_ATLAS_TEXTURE).bindTexture();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(0.85f, 0.85f, 0.85f);

        float tickDelta = Minecraft.INSTANCE.timer.tickDelta;

        GL11.glRotatef(boomerang.prevPitch + (boomerang.pitch - boomerang.prevPitch) * tickDelta, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(boomerang.prevYaw + (boomerang.yaw - boomerang.prevYaw) * tickDelta - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
//        if (renderOutlines) {
//            GL11.enableColorMaterial();
//            GL11.enableOutlineMode(getTeamColor(boomerang));
//        }
        BakedModelRenderer bakedModelRenderer = RendererAccess.INSTANCE.getRenderer().bakedModelRenderer();
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.startQuads();
        bakedModelRenderer.renderItem(getStackToRender(boomerang), ModelTransformation.Mode.NONE, entity.getBrightnessAtEyes(1), bakedModelRenderer.getModel(getStackToRender(boomerang), null, null, entity.id + ModelTransformation.Mode.NONE.ordinal()));
        tessellator.draw();
//        if (renderOutlines) {
//            GL11.disableOutlineMode();
//            GL11.disableColorMaterial();
//        }
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    public ItemStack getStackToRender(BoomerangEntity entity) {
        return entity.getWeapon() != null ? entity.getWeapon() : new ItemStack(Item.DIAMOND);
    }
}
