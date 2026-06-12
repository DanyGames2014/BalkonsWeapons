package net.danygames2014.balkonsweapons.client.render.entity;

import net.danygames2014.balkonsweapons.client.render.entity.model.CannonBarrelModel;
import net.danygames2014.balkonsweapons.client.render.entity.model.LegacyCannonModel;
import net.danygames2014.balkonsweapons.client.render.entity.model.StandardCannonModel;
import net.danygames2014.balkonsweapons.entity.CannonEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class CannonEntityRenderer extends EntityRenderer {
    private final CannonBarrelModel barrelModel = new CannonBarrelModel();
    private final StandardCannonModel standardModel = new StandardCannonModel();
    private final LegacyCannonModel legacyModel = new LegacyCannonModel();

    @Override
    public void render(Entity entity, double x, double y, double z, float yaw, float pitch) {
        float tickDelta = Minecraft.INSTANCE.timer.tickDelta;
        CannonEntity cannon = (CannonEntity) entity;
        yaw = interpolateRotation(cannon.prevYaw, cannon.yaw, tickDelta);
        GL11.glPushMatrix();

        boolean legacyCannonModel = true;

        if (legacyCannonModel) {
            GL11.glTranslated(x, y - 0.2, z);
            GL11.glRotatef(-yaw, 0.0f, 1.0f, 0.0f);
        } else {
            GL11.glTranslated(x, y + 2.1, z);
            GL11.glRotatef(180.0f - yaw, 0.0f, 1.0f, 0.0f);
        }

        float f2 = cannon.getTimeSinceHit() - tickDelta;
        float f3 = cannon.getCurrentDamage() - tickDelta;
        if (f3 < 0.0f) f3 = 0.0f;
        if (f2 > 0.0f)
            GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0f * cannon.getRockDirection() / 5.0f,
                    0.0f, 0.0f, 1.0f);
        bindTexture(getEntityTexture(cannon));
        if (cannon.isSuperPowered() && cannon.age % 5 < 2) {
            float f4 = 1.5f;
            GL11.glColor3f(cannon.getBrightnessAtEyes(tickDelta) * f4,
                    cannon.getBrightnessAtEyes(tickDelta) * f4,
                    cannon.getBrightnessAtEyes(tickDelta) * f4);
        }

        if (legacyCannonModel) {
            GL11.glScalef(-1.0f, -1.0f, 1.0f);
            GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
            legacyModel.barrel.pitch = Math.max(-cannon.pitch / 120.0f, -0.25f);
            legacyModel.render(0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        } else {
            float rot = cannon.prevPitch +
                                (cannon.pitch - cannon.prevPitch) * tickDelta;
            rot = Math.min(rot, 20.0f);
            GL11.glScalef(-1.6f, -1.6f, 1.6f);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 1.0f, 0.0f);
            GL11.glRotatef(rot, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(0.0f, -1.0f, 0.0f);
            barrelModel.render(tickDelta, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
            GL11.glPopMatrix();
            float yawRadians = -(float) Math.toRadians(yaw);
            standardModel.base1.yaw = yawRadians;
            standardModel.base2.yaw = yawRadians;
            standardModel.baseStand.yaw = yawRadians;
            standardModel.render(tickDelta, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        }

        GL11.glPopMatrix();
    }

    private float interpolateRotation(float from, float to, float by) {
        return (from + wrapAngleTo180Float(to - from) * by) % 360.0f;
    }

    public float wrapAngleTo180Float(float angle)
    {
        angle %= 360.0F;

        if (angle >= 180.0F)
        {
            angle -= 360.0F;
        }

        if (angle < -180.0F)
        {
            angle += 360.0F;
        }

        return angle;
    }

    protected String getEntityTexture(Entity entity) {
        return false
                       ? "/assets/balkonsweapons/stationapi/textures/entity/cannon_legacy.png"
                       : "/assets/balkonsweapons/stationapi/textures/entity/cannon.png";
    }
}
