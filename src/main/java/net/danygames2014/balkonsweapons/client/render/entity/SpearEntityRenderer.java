package net.danygames2014.balkonsweapons.client.render.entity;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.balkonsweapons.config.Config;
import net.danygames2014.balkonsweapons.entity.projectile.BoomerangEntity;
import net.danygames2014.balkonsweapons.entity.projectile.SpearEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.modificationstation.stationapi.api.client.StationRenderAPI;
import net.modificationstation.stationapi.api.client.render.RendererAccess;
import net.modificationstation.stationapi.api.client.render.model.BakedModelRenderer;
import net.modificationstation.stationapi.api.client.render.model.json.ModelTransformation;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import org.lwjgl.opengl.EXTRescaleNormal;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class SpearEntityRenderer extends EntityRenderer {
    @Override
    public void render(Entity entity, double x, double y, double z, float yaw, float pitch) {
        SpearEntity spear = (SpearEntity) entity;
        float tickDelta = Minecraft.INSTANCE.timer.tickDelta;
        if(Config.LEGACY_CONFIG.legacySpear) {
            bindTexture("/assets/balkonsweapons/stationapi/textures/entity/spear.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glTranslated(x, y, z);
            GL11.glRotatef(spear.prevYaw + (spear.yaw - spear.prevYaw) * tickDelta - 90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(spear.prevPitch + (spear.pitch - spear.prevPitch) * tickDelta, 0.0f, 0.0f, 1.0f);
            Tessellator tess = Tessellator.INSTANCE;
            float[] color = spear.getMaterialColor();
            double length = 20.0;
            GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
            float f13 = spear.shake - tickDelta;
            if (f13 > 0.0f) {
                float f14 = -MathHelper.sin(f13 * 3.0f) * f13;
                GL11.glRotatef(f14, 0.0f, 0.0f, 1.0f);
            }
            GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
            GL11.glScalef(0.05625f, 0.05625f, 0.05625f);
            GL11.glTranslatef(-4.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.05625f, 0.0f, 0.0f);
            tess.startQuads();
            tess.vertex(-length, -2.0, -2.0, 0.0, 0.15625);
            tess.vertex(-length, -2.0, 2.0, 0.15625, 0.15625);
            tess.vertex(-length, 2.0, 2.0, 0.15625, 0.3125);
            tess.vertex(-length, 2.0, -2.0, 0.0, 0.3125);
            tess.draw();
            GL11.glNormal3f(-0.05625f, 0.0f, 0.0f);
            tess.startQuads();
            tess.vertex(-length, 2.0, -2.0, 0.0, 0.15625);
            tess.vertex(-length, 2.0, 2.0, 0.15625, 0.15625);
            tess.vertex(-length, -2.0, 2.0, 0.15625, 0.3125);
            tess.vertex(-length, -2.0, -2.0, 0.0, 0.3125);
            tess.draw();
            for (int j = 0; j < 4; ++j) {
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glNormal3f(0.0f, 0.0f, 0.05625f);
                tess.startQuads();
                tess.color(1F, 1F, 1F);
                tess.vertex(-length, -2.0, 0.0, 0.0, 0.0);
                tess.vertex(length, -2.0, 0.0, 1.0, 0.0);
                tess.vertex(length, 2.0, 0.0, 1.0, 0.15625);
                tess.vertex(-length, 2.0, 0.0, 0.0, 0.15625);
                tess.color(color[0], color[1], color[2]);
                tess.vertex(-length, -2.0, 0.0, 0.0, 0.3125);
                tess.vertex(length, -2.0, 0.0, 1.0, 0.3125);
                tess.vertex(length, 2.0, 0.0, 1.0, 0.46875);
                tess.vertex(-length, 2.0, 0.0, 0.0, 0.46875);
                tess.draw();
            }
            GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        } else {
            GL11.glPushMatrix();
            StationRenderAPI.getBakedModelManager().getAtlas(Atlases.GAME_ATLAS_TEXTURE).bindTexture();
            GL11.glTranslated(x, y, z);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(1.7f, 1.7f, 1.7f);
            GL11.glRotatef(spear.prevYaw + (spear.yaw - spear.prevYaw) * tickDelta - 90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(spear.prevPitch + (spear.pitch - spear.prevPitch) * tickDelta - 45.0f, 0.0f, 0.0f, 1.0f);

            float f15 = spear.shake - tickDelta;
            if (f15 > 0.0f) {
                float f16 = -MathHelper.sin(f15 * 3.0f) * f15;
                GL11.glRotatef(f16, 0.0f, 0.0f, 1.0f);
            }
            GL11.glTranslatef(-0.35f, -0.35f, 0.0f);
            BakedModelRenderer bakedModelRenderer = RendererAccess.INSTANCE.getRenderer().bakedModelRenderer();
            Tessellator tessellator = Tessellator.INSTANCE;
            tessellator.startQuads();
            bakedModelRenderer.renderItem(getStackToRender(spear), ModelTransformation.Mode.NONE, entity.getBrightnessAtEyes(1), bakedModelRenderer.getModel(getStackToRender(spear), null, null, entity.id + ModelTransformation.Mode.NONE.ordinal()));
            tessellator.draw();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

    public ItemStack getStackToRender(SpearEntity entity) {
        return entity.getWeapon() != null ? entity.getWeapon() : new ItemStack(BalkonsWeapons.woodenSpear);
    }
}
