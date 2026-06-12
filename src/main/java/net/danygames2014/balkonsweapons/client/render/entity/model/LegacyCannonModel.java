package net.danygames2014.balkonsweapons.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public class LegacyCannonModel extends EntityModel {
    public final ModelPart barrel;
    public final ModelPart bottom;
    public final ModelPart frame;
    public final ModelPart seatBottom;
    public final ModelPart seatFrame;

    public LegacyCannonModel() {
        barrel = new ModelPart(0, 14);
        barrel.addCuboid(-4.0f, 0.0f, -16.0f, 6, 6, 12, 4.0f);
        barrel.setPivot(0.0f, 9.0f, 1.0f);
        barrel.pitch = -6.195919f;
        barrel.mirror = true;
        bottom = new ModelPart( 0, 1);
        bottom.addCuboid(0.0f, 0.0f, 0.0f, 12, 1, 12, 1.0f);
        bottom.setPivot(-7.0f, -1.0f, -5.0f);
        bottom.mirror = true;
        frame = new ModelPart( 24, 19);
        frame.addCuboid(0.0f, 0.0f, 0.0f, 2, 2, 2, 1.0f);
        frame.setPivot(-2.0f, 2.0f, 0.0f);
        frame.mirror = true;
        seatBottom = new ModelPart( 6, 5);
        seatBottom.addCuboid(0.0f, 0.0f, 0.0f, 10, 1, 8, 1.0f);
        seatBottom.setPivot(-6.0f, 8.0f, 8.0f);
        seatBottom.mirror = true;
        seatFrame = new ModelPart( 36, 19);
        seatFrame.addCuboid(0.0f, 0.0f, 0.0f, 2, 1, 12, 1.0f);
        seatFrame.setPivot(-2.0f, 6.0f, 0.0f);
        seatFrame.mirror = true;
    }

    @Override
    public void render(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        super.render(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
        setAngles(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);

        barrel.render(scale);
        bottom.render(scale);
        frame.render(scale);
        seatBottom.render(scale);
        seatFrame.render(scale);
    }

    public void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }
}
