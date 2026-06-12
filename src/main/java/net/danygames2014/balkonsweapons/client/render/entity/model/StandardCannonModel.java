package net.danygames2014.balkonsweapons.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public class StandardCannonModel extends EntityModel {
    public final ModelPart consoleMain;
    public final ModelPart consoleSideL1;
    public final ModelPart consoleSideR1;
    public final ModelPart base1;
    public final ModelPart base2;
    public final ModelPart baseStand;
    public final ModelPart axis1;

    public StandardCannonModel() {
        consoleMain = new ModelPart( 12, 20);
        consoleMain.addCuboid(-2.5f, -1.0f, -1.0f, 5, 1, 2);
        consoleMain.setPivot(0.0f, 20.0f, 0.0f);
        consoleMain.mirror = true;
        setRotation(consoleMain, 0.0f, 0.0f, 0.0f);
        consoleSideL1 = new ModelPart( 26, 20);
        consoleSideL1.addCuboid(2.5f, -4.0f, -1.0f, 1, 5, 2);
        consoleSideL1.setPivot(0.0f, 19.0f, 0.0f);
        consoleSideL1.mirror = true;
        setRotation(consoleSideL1, 0.0f, 0.0f, 0.0f);
        consoleSideR1 = new ModelPart( 26, 20);
        consoleSideR1.addCuboid(-3.5f, -4.0f, -1.0f, 1, 5, 2);
        consoleSideR1.setPivot(0.0f, 19.0f, 0.0f);
        consoleSideR1.mirror = true;
        setRotation(consoleSideR1, 0.0f, 0.0f, 0.0f);
        base1 = new ModelPart( 0, 26);
        base1.addCuboid(-2.0f, -2.0f, -2.0f, 4, 2, 4);
        base1.setPivot(0.0f, 24.0f, 0.0f);
        base1.mirror = true;
        setRotation(base1, 0.0f, 0.0f, 0.0f);
        base2 = new ModelPart( 16, 28);
        base2.addCuboid(-1.5f, -3.0f, -1.5f, 3, 1, 3);
        base2.setPivot(0.0f, 24.0f, 0.0f);
        base2.mirror = true;
        setRotation(base2, 0.0f, 0.0f, 0.0f);
        baseStand = new ModelPart(0, 23);
        baseStand.addCuboid(-1.0f, -4.0f, -1.0f, 2, 1, 2);
        baseStand.setPivot(0.0f, 24.0f, 0.0f);
        baseStand.mirror = true;
        setRotation(baseStand, 0.0f, 0.0f, 0.0f);
        axis1 = new ModelPart(22, 23);
        axis1.addCuboid(-0.5f, -5.5f, -0.5f, 1, 3, 1);
        axis1.setPivot(0.0f, 24.0f, 0.0f);
        axis1.mirror = true;
        setRotation(axis1, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        super.render(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
        setAngles(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);

        consoleMain.render(scale);
        consoleSideL1.render(scale);
        consoleSideR1.render(scale);
        base1.render(scale);
        base2.render(scale);
        baseStand.render(scale);
        axis1.render(scale);
    }

    public void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }
}
