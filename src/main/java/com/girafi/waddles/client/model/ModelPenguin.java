package com.girafi.waddles.client.model;

import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class ModelPenguin extends BipedEntityModel<EntityAdeliePenguin> {
    private Cuboid head;
    private Cuboid body;
    private Cuboid beak;
    private Cuboid flipperRight;
    private Cuboid flipperLeft;
    private Cuboid feetLeft;
    private Cuboid feetRight;
    private Cuboid tail;

    public ModelPenguin() {
        this.beak = new Cuboid(this, 18, 0);
        this.beak.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.beak.addBox(-0.5F, -3.0F, -4.0F, 1, 2, 3, 0.0F);
        this.setRotateAngle(beak, 0.08726646259971647F, -0.0F, 0.0F);
        this.body = new Cuboid(this, 0, 9);
        this.body.setRotationPoint(0.0F, 12.0F, 1.0F);
        this.body.addBox(-2.5F, 0.0F, -2.0F, 5, 11, 5, 0.0F);
        this.feetRight = new Cuboid(this, 0, 25);
        this.feetRight.setRotationPoint(-1.0F, 11.0F, 0.0F);
        this.feetRight.addBox(-2.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(feetRight, 0.0F, 0.2617993877991494F, 0.0F);
        this.head = new Cuboid(this, 0, 0);
        this.head.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.head.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 5, 0.0F);
        this.tail = new Cuboid(this, 20, 20);
        this.tail.setRotationPoint(0.0F, 11.0F, 3.0F);
        this.tail.addBox(-1.5F, -1.0F, 0.0F, 3, 3, 1, 0.0F);
        this.setRotateAngle(tail, 1.2566370614359172F, 0.0F, 0.0F);
        this.flipperRight = new Cuboid(this, 20, 10);
        this.flipperRight.setRotationPoint(-2.5F, 1.0F, 0.0F);
        this.flipperRight.addBox(-1.0F, 0.0F, -1.0F, 1, 7, 3, 0.0F);
        this.setRotateAngle(flipperRight, 0.0F, 0.0F, 0.08726646259971647F);
        this.feetLeft = new Cuboid(this, 0, 25);
        this.feetLeft.mirror = true;
        this.feetLeft.setRotationPoint(1.0F, 11.0F, 0.0F);
        this.feetLeft.addBox(0.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(feetLeft, 0.0F, -0.2617993877991494F, 0.0F);
        this.flipperLeft = new Cuboid(this, 20, 10);
        this.flipperLeft.mirror = true;
        this.flipperLeft.setRotationPoint(2.5F, 1.0F, 0.0F);
        this.flipperLeft.addBox(0.0F, 0.0F, -1.0F, 1, 7, 3, 0.0F);
        this.setRotateAngle(flipperLeft, 0.0F, 0.0F, -0.08726646259971647F);
        this.head.addChild(this.beak);
        this.body.addChild(this.flipperRight);
        this.body.addChild(this.flipperLeft);
        this.body.addChild(this.feetRight);
        this.body.addChild(this.feetLeft);
        this.body.addChild(this.tail);
    }

    @Override
    public void render(EntityAdeliePenguin penguin, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setAngles(penguin, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if (this.isChild) {
            float f = 2.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.0F, 6.0F * scale, 0.0F);
            this.head.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(1.4F / f, 1.0F / f, 1.2F / f);
            GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
            this.body.render(scale);
            GlStateManager.popMatrix();
        } else {
            this.head.render(scale);
            this.body.render(scale);
        }
    }

    private void setRotateAngle(Cuboid cuboid, float x, float y, float z) {
        cuboid.pitch = x;
        cuboid.yaw = y;
        cuboid.roll = z;
    }

    @Override
    public void setAngles(EntityAdeliePenguin penguin, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.head.pitch = headPitch * 0.017453292F;
        this.head.yaw = netHeadYaw * 0.017453292F;
        this.head.roll = (MathHelper.cos(limbSwing * 1.3324F) * 1.4F * limbSwingAmount) / 6;
        this.body.roll = (MathHelper.cos(limbSwing * 1.3324F) * 1.4F * limbSwingAmount) / 6;
        this.feetRight.pitch = MathHelper.cos(limbSwing * 1.3324F) * 1.2F * limbSwingAmount;
        this.feetLeft.pitch = MathHelper.cos(limbSwing * 1.3324F + (float) Math.PI) * 1.2F * limbSwingAmount;
        this.flipperRight.roll = 0.08726646259971647F + (MathHelper.cos((float) penguin.rotationFlipper) * limbSwingAmount);
        this.flipperLeft.roll = -0.08726646259971647F + (MathHelper.cos((float) penguin.rotationFlipper + (float) Math.PI) * limbSwingAmount);
        this.tail.yaw = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * 1.4F * limbSwingAmount;

        //Roation angles when swimming
        //this.body.rotateAngleX = penguin.bodyRotation;

        /*this.flipperRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F; //TODO
        this.flipperLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;*/
    }
}