package net.danygames2014.balkonsweapons.client.render.entity;

import net.danygames2014.balkonsweapons.entity.projectile.CrossbowBoltEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.EXTRescaleNormal;
import org.lwjgl.opengl.GL11;

public class CrossbowBoltEntityRenderer extends EntityRenderer {
    @Override
    public void render(Entity entity, double x, double y, double z, float yaw, float pitch) {
        float tickDelta = Minecraft.INSTANCE.timer.tickDelta;
        CrossbowBoltEntity bolt = (CrossbowBoltEntity) entity;
        bindTexture("/assets/balkonsweapons/stationapi/textures/entity/crossbow_bolt.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(bolt.prevYaw + (bolt.yaw - bolt.prevYaw) * tickDelta - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(bolt.prevPitch + (bolt.pitch - bolt.prevPitch) * tickDelta,
                0.0f, 0.0f, 1.0f);
        Tessellator tess = Tessellator.INSTANCE;
        GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        float f11 = bolt.shake - tickDelta;
        if (f11 > 0.0f) {
            float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
            GL11.glRotatef(f12, 0.0f, 0.0f, 1.0f);
        }
        GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(0.05625f, 0.05625f, 0.05625f);
        GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
        GL11.glNormal3f(0.05625f, 0.0f, 0.0f);
        tess.startQuads();
        tess.vertex(-5.0, -2.0, -2.0, 0.0, 0.15625);
        tess.vertex(-5.0, -2.0, 2.0, 0.15625, 0.15625);
        tess.vertex(-5.0, 2.0, 2.0, 0.15625, 0.3125);
        tess.vertex(-5.0, 2.0, -2.0, 0.0, 0.3125);
        tess.draw();
        GL11.glNormal3f(-0.05625f, 0.0f, 0.0f);
        tess.startQuads();
        tess.vertex(-5.0, 2.0, -2.0, 0.0, 0.15625);
        tess.vertex(-5.0, 2.0, 2.0, 0.15625, 0.15625);
        tess.vertex(-5.0, -2.0, 2.0, 0.15625, 0.3125);
        tess.vertex(-5.0, -2.0, -2.0, 0.0, 0.3125);
        tess.draw();
        for (int j = 0; j < 4; ++j) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, 0.05625f);
            tess.startQuads();
            tess.vertex(-6.0, -2.0, 0.0, 0.0, 0.0);
            tess.vertex(6.0, -2.0, 0.0, 0.5, 0.0);
            tess.vertex(6.0, 2.0, 0.0, 0.5, 0.15625);
            tess.vertex(-6.0, 2.0, 0.0, 0.0, 0.15625);
            tess.draw();
        }
        GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
