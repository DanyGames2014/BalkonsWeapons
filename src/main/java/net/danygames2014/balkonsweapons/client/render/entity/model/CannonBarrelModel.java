package net.danygames2014.balkonsweapons.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public class CannonBarrelModel extends EntityModel {
    public final ModelPart swivelFront;
    public final ModelPart swivelBack;
    public final ModelPart swivelMain;
    public final ModelPart axis;
    public final ModelPart seal;
    public final ModelPart handCrap;
    public final ModelPart frontTip;
    public final ModelPart backTip;

    public CannonBarrelModel() {
        swivelFront = new ModelPart(12, 12);
        swivelFront.addCuboid(-2.0f, -2.0f, -11.0f, 4, 4, 2);
        swivelFront.setPivot(0.0f, 16.0f, 0.0f);
        swivelFront.mirror = true;
        setRotation(swivelFront, 0.0f, 0.0f, 0.0f);
        swivelBack = new ModelPart(12, 0);
        swivelBack.addCuboid(-2.0f, -0.501f, -2.0f, 4, 8, 4);
        swivelBack.setPivot(0.0f, 16.0f, 0.0f);
        swivelBack.mirror = true;
        setRotation(swivelBack, 1.570796f, 0.0f, 0.0f);
        swivelMain = new ModelPart(0, 0);
        swivelMain.addCuboid(-1.5f, -11.5f, -1.5f, 3, 20, 3);
        swivelMain.setPivot(0.0f, 16.0f, 0.0f);
        swivelMain.mirror = true;
        setRotation(swivelMain, 1.570796f, 0.0f, 0.0f);
        axis = new ModelPart(12, 18);
        axis.addCuboid(-4.0f, -0.5f, -0.5f, 8, 1, 1);
        axis.setPivot(0.0f, 16.0f, 0.0f);
        axis.mirror = true;
        setRotation(axis, 1.570796f, 0.0f, 0.0f);
        seal = new ModelPart(9, 0);
        seal.addCuboid(-1.0f, -2.5f, 5.5f, 2, 1, 1);
        seal.setPivot(0.0f, 16.0f, 0.0f);
        seal.mirror = true;
        setRotation(seal, 0.0f, 0.0f, 0.0f);
        handCrap = new ModelPart(28, 0);
        handCrap.addCuboid(-0.5f, 8.5f, -0.5f, 1, 7, 1);
        handCrap.setPivot(0.0f, 16.0f, 0.0f);
        handCrap.mirror = true;
        setRotation(handCrap, 1.570796f, 0.0f, 0.0f);
        frontTip = new ModelPart(24, 12);
        frontTip.addCuboid(-1.0f, 9.0f, -1.0f, 2, 1, 2);
        frontTip.setPivot(0.0f, 16.0f, 0.0f);
        frontTip.mirror = true;
        setRotation(frontTip, 1.570796f, 0.0f, 0.0f);
        backTip = new ModelPart(24, 12);
        backTip.addCuboid(-1.0f, 15.5f, -1.0f, 2, 1, 2);
        backTip.setPivot(0.0f, 16.0f, 0.0f);
        backTip.mirror = true;
        setRotation(backTip, 1.570796f, 0.0f, 0.0f);
    }

    @Override
    public void render(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        super.render(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
        setAngles(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
        swivelFront.render(scale);
        swivelBack.render(scale);
        swivelMain.render(scale);
        axis.render(scale);
        seal.render(scale);
        handCrap.render(scale);
        frontTip.render(scale);
        backTip.render(scale);
    }

    public void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }
}
