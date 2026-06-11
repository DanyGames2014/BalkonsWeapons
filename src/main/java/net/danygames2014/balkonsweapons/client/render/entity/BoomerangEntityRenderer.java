package net.danygames2014.balkonsweapons.client.render.entity;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
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
        float tickDelta = Minecraft.INSTANCE.timer.tickDelta;
        if(false) {
            bindTexture("/assets/balkonsweapons/stationapi/textures/entity/boomerang.png");
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glTranslated(x, y, z);
            GL11.glRotatef(boomerang.prevPitch + (boomerang.pitch - boomerang.prevPitch) * tickDelta, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(boomerang.prevYaw + (boomerang.yaw - boomerang.prevYaw) * tickDelta - 90.0f, 0.0f, 1.0f, 0.0f);
            Tessellator tess = Tessellator.INSTANCE;
            int mat = boomerang.getWeaponMaterialId();
            float[] color = boomerang.getMaterialColor();
            GL11.glTranslatef(-0.5f, 0.0f, -0.5f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            tess.startQuads();
            tess.color(1F, 1F, 1F);
            tess.vertex(0.0, 0.0, 1.0, 0.5, 0.0);
            tess.vertex(1.0, 0.0, 1.0, 0.0, 0.0);
            tess.vertex(1.0, 0.0, 0.0, 0.0, 0.5);
            tess.vertex(0.0, 0.0, 0.0, 0.5, 0.5);
            if (mat != 0) {
                tess.color(color[0], color[1], color[2]);
                tess.vertex(0.0, 0.0, 1.0, 1.0, 0.0);
                tess.vertex(1.0, 0.0, 1.0, 0.5, 0.0);
                tess.vertex(1.0, 0.0, 0.0, 0.5, 0.5);
                tess.vertex(0.0, 0.0, 0.0, 1.0, 0.5);
            }
            tess.draw();
            GL11.glNormal3f(0.0f, -1.0f, 0.0f);
            tess.startQuads();
            tess.color(1F, 1F, 1F);
            tess.vertex(1.0, 0.0, 0.0, 0.0, 0.5);
            tess.vertex(1.0, 0.0, 1.0, 0.5, 0.5);
            tess.vertex(0.0, 0.0, 1.0, 0.5, 0.0);
            tess.vertex(0.0, 0.0, 0.0, 0.0, 0.0);
            if (mat != 0) {
                tess.color(color[0], color[1], color[2]);
                tess.vertex(1.0, 0.0, 0.0, 0.5, 0.5);
                tess.vertex(1.0, 0.0, 1.0, 1.0, 0.5);
                tess.vertex(0.0, 0.0, 1.0, 1.0, 0.0);
                tess.vertex(0.0, 0.0, 0.0, 0.5, 0.0);
            }
            tess.draw();
            float sqrt2 = (float) Math.sqrt(2.0);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glNormal3f(-sqrt2, 0.0f, sqrt2);
            tess.startQuads();
            tess.color(1F, 1F, 1F);
            tess.vertex(0.2, -0.08, 0.8, 0.5, 0.5);
            tess.vertex(0.2, 0.08, 0.8, 0.5, 0.65625);
            tess.vertex(0.9, 0.08, 0.8, 0.0, 0.65625);
            tess.vertex(0.9, -0.08, 0.8, 0.0, 0.5);
            if (mat != 0) {
                tess.color(color[0], color[1], color[2]);
                tess.vertex(0.2, -0.08, 0.8, 1.0, 0.5);
                tess.vertex(0.2, 0.08, 0.8, 1.0, 0.65625);
                tess.vertex(0.9, 0.08, 0.8, 0.5, 0.65625);
                tess.vertex(0.9, -0.08, 0.8, 0.5, 0.5);
            }
            tess.color(1F, 1F, 1F);
            tess.vertex(0.2, -0.08, 0.8, 0.5, 0.5);
            tess.vertex(0.2, 0.08, 0.8, 0.5, 0.65625);
            tess.vertex(0.2, 0.08, 0.2, 0.0, 0.65625);
            tess.vertex(0.2, -0.08, 0.2, 0.0, 0.5);
            if (mat != 0) {
                tess.color(color[0], color[1], color[2]);
                tess.vertex(0.2, -0.08, 0.8, 1.0, 0.5);
                tess.vertex(0.2, 0.08, 0.8, 1.0, 0.65625);
                tess.vertex(0.2, 0.08, 0.2, 0.5, 0.65625);
                tess.vertex(0.2, -0.08, 0.2, 0.5, 0.5);
            }
            tess.draw();
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        } else {
            GL11.glPushMatrix();
            StationRenderAPI.getBakedModelManager().getAtlas(Atlases.GAME_ATLAS_TEXTURE).bindTexture();
            GL11.glTranslated(x, y, z);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(0.85f, 0.85f, 0.85f);

            GL11.glRotatef(boomerang.prevPitch + (boomerang.pitch - boomerang.prevPitch) * tickDelta, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(boomerang.prevYaw + (boomerang.yaw - boomerang.prevYaw) * tickDelta - 90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            BakedModelRenderer bakedModelRenderer = RendererAccess.INSTANCE.getRenderer().bakedModelRenderer();
            Tessellator tessellator = Tessellator.INSTANCE;
            tessellator.startQuads();
            bakedModelRenderer.renderItem(getStackToRender(boomerang), ModelTransformation.Mode.NONE, entity.getBrightnessAtEyes(1), bakedModelRenderer.getModel(getStackToRender(boomerang), null, null, entity.id + ModelTransformation.Mode.NONE.ordinal()));
            tessellator.draw();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

    public ItemStack getStackToRender(BoomerangEntity entity) {
        return entity.getWeapon() != null ? entity.getWeapon() : new ItemStack(BalkonsWeapons.woodenBoomerang);
    }
}
