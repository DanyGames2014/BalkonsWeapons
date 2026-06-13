package net.danygames2014.balkonsweapons.client.render.entity;

import net.danygames2014.balkonsweapons.client.render.entity.model.DummyModel;
import net.danygames2014.balkonsweapons.entity.DummyEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class DummyEntityRenderer extends EntityRenderer {
    private final DummyModel dummyModel = new DummyModel();

    public DummyEntityRenderer() {
        shadowRadius = 0.7F;
    }

    @Override
    public void render(Entity entity, double x, double y, double z, float yaw, float pitch) {
        DummyEntity dummy = (DummyEntity) entity;
        float tickDelta = Minecraft.INSTANCE.timer.tickDelta;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y - 0.025f, z);
        GL11.glRotatef(180.0f - yaw, 0.0f, 1.0f, 0.0f);
        float f2 = dummy.getTimeSinceHit() - tickDelta;
        float f3 = dummy.getCurrentDamage() - tickDelta;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0f * dummy.getRockDirection() / 5.0f, 0.0f,
                    0.0f, 1.0f);
        }
        bindTexture("/assets/balkonsweapons/stationapi/textures/entity/dummy.png");
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        dummyModel.render(tickDelta, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
}
