package net.danygames2014.balkonsweapons.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class MortarShellEntityRenderer extends EntityRenderer {

    public MortarShellEntityRenderer() {
        shadowRadius = 0.3F;
    }

    @Override
    public void render(Entity entity, double x, double y, double z, float yaw, float pitch) {
        Tessellator tess = Tessellator.INSTANCE;
        GL11.glPushMatrix();
        bindTexture("/assets/balkonsweapons/stationapi/textures/entity/cannon_ball.png");
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(180.0f - yaw, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glScalef(0.2f, 0.2f, 0.2f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        tess.startQuads();
        tess.vertex(-0.5, 0.5, -0.5, 0.0, 1.0);
        tess.vertex(0.5, 0.5, -0.5, 1.0, 1.0);
        tess.vertex(0.5, -0.5, -0.5, 1.0, 0.0);
        tess.vertex(-0.5, -0.5, -0.5, 0.0, 0.0);
        tess.vertex(-0.5, -0.5, 0.5, 0.0, 0.0);
        tess.vertex(0.5, -0.5, 0.5, 1.0, 0.0);
        tess.vertex(0.5, 0.5, 0.5, 1.0, 1.0);
        tess.vertex(-0.5, 0.5, 0.5, 0.0, 1.0);
        tess.vertex(-0.5, -0.5, -0.5, 0.0, 0.0);
        tess.vertex(0.5, -0.5, -0.5, 1.0, 0.0);
        tess.vertex(0.5, -0.5, 0.5, 1.0, 1.0);
        tess.vertex(-0.5, -0.5, 0.5, 0.0, 1.0);
        tess.vertex(-0.5, 0.5, 0.5, 0.0, 1.0);
        tess.vertex(0.5, 0.5, 0.5, 1.0, 1.0);
        tess.vertex(0.5, 0.5, -0.5, 1.0, 0.0);
        tess.vertex(-0.5, 0.5, -0.5, 0.0, 0.0);
        tess.vertex(-0.5, -0.5, 0.5, 0.0, 0.0);
        tess.vertex(-0.5, 0.5, 0.5, 1.0, 0.0);
        tess.vertex(-0.5, 0.5, -0.5, 1.0, 1.0);
        tess.vertex(-0.5, -0.5, -0.5, 0.0, 1.0);
        tess.vertex(0.5, -0.5, -0.5, 0.0, 0.0);
        tess.vertex(0.5, 0.5, -0.5, 1.0, 0.0);
        tess.vertex(0.5, 0.5, 0.5, 1.0, 1.0);
        tess.vertex(0.5, -0.5, 0.5, 0.0, 1.0);
        tess.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
