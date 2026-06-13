package net.danygames2014.balkonsweapons.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public class DummyModel extends EntityModel {
    public final ModelPart armLeft;
    public final ModelPart armRight;
    public final ModelPart body;
    public final ModelPart head;
    public final ModelPart stick;
    public final ModelPart inside;

    public DummyModel() {
        armLeft = new ModelPart( 0, 24);
        armLeft.addCuboid(0.0f, 0.0f, 0.0f, 10, 4, 4);
        armLeft.setPivot(6.0f, 18.0f, -2.0f);
        armRight = new ModelPart( 0, 24);
        armRight.addCuboid(-10.0f, 0.0f, 0.0f, 10, 4, 4);
        armRight.setPivot(-6.0f, 18.0f, -2.0f);
        body = new ModelPart( 40, 0);
        body.addCuboid(0.0f, 0.0f, 0.0f, 6, 8, 6, 3.0f);
        body.setPivot(-3.0f, 11.0f, -3.0f);
        inside = new ModelPart( 40, 14);
        inside.addCuboid(0.0f, 0.0f, 0.0f, 6, 8, 6, 2.0f);
        inside.setPivot(-3.0f, 11.0f, -3.0f);
        head = new ModelPart( 0, 0);
        head.addCuboid(-5.0f, 0.0f, -5.0f, 6, 6, 6, 2.0f);
        head.setPivot(1.5f, 25.0f, 1.5f);
        stick = new ModelPart( 24, 0);
        stick.addCuboid(0.0f, 0.0f, 0.0f, 4, 10, 4);
        stick.setPivot(-2.0f, 0.0f, -2.0f);
    }

    @Override
    public void render(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        armLeft.render(scale);
        armRight.render(scale);
        body.render(scale);
        inside.render(scale);
        head.render(scale);
        stick.render(scale);
    }
}
