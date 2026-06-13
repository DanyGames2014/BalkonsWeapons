package net.danygames2014.balkonsweapons.client.render.entity;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.EXTRescaleNormal;
import org.lwjgl.opengl.GL11;

public class BlunderShotEntityRenderer extends EntityRenderer {
    @Override
    public void render(Entity entity, double x, double y, double z, float yaw, float pitch) {
        bindTexture("/assets/balkonsweapons/stationapi/textures/entity/musket_bullet.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(x, y, z);
        Tessellator tess = Tessellator.INSTANCE;
        GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        GL11.glScalef(0.04f, 0.04f, 0.04f);
        GL11.glNormal3f(0.05625f, 0.0f, 0.0f);
        tess.startQuads();
        tess.vertex(0.0, -1.0, -1.0, 0.0, 0.0);
        tess.vertex(0.0, -1.0, 1.0, 0.3125, 0.0);
        tess.vertex(0.0, 1.0, 1.0, 0.3125, 0.3125);
        tess.vertex(0.0, 1.0, -1.0, 0.0, 0.3125);
        tess.draw();
        GL11.glNormal3f(-0.05625f, 0.0f, 0.0f);
        tess.startQuads();
        tess.vertex(0.0, 1.0, -1.0, 0.0, 0.0);
        tess.vertex(0.0, 1.0, 1.0, 0.3125, 0.0);
        tess.vertex(0.0, -1.0, 1.0, 0.3125, 0.3125);
        tess.vertex(0.0, -1.0, -1.0, 0.0, 0.3125);
        tess.draw();
        for (int j = 0; j < 4; ++j) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, 0.05625f);
            tess.startQuads();
            tess.vertex(-1.0, -1.0, 0.0, 0.0, 0.0);
            tess.vertex(1.0, -1.0, 0.0, 0.3125, 0.0);
            tess.vertex(1.0, 1.0, 0.0, 0.3125, 0.3125);
            tess.vertex(-1.0, 1.0, 0.0, 0.0, 0.3125);
            tess.draw();
        }
        GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
