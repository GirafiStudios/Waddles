package com.girafi.waddles.client.model;

import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class PenguinModel<T extends EntityAdeliePenguin> extends EntityModel<T> {
    private RendererModel head;
    private RendererModel body;
    private RendererModel beak;
    private RendererModel flipperRight;
    private RendererModel flipperLeft;
    private RendererModel feetLeft;
    private RendererModel feetRight;
    private RendererModel tail;

    public PenguinModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.beak = new RendererModel(this, 18, 0);
        this.beak.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.beak.addBox(-0.5F, -3.0F, -4.0F, 1, 2, 3, 0.0F);
        this.setRotateAngle(beak, 0.08726646259971647F, -0.0F, 0.0F);
        this.body = new RendererModel(this, 0, 9);
        this.body.setRotationPoint(0.0F, 12.0F, 1.0F);
        this.body.addBox(-2.5F, 0.0F, -2.0F, 5, 11, 5, 0.0F);
        this.feetRight = new RendererModel(this, 0, 25);
        this.feetRight.setRotationPoint(-1.0F, 11.0F, 0.0F);
        this.feetRight.addBox(-2.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(feetRight, 0.0F, 0.2617993877991494F, 0.0F);
        this.head = new RendererModel(this, 0, 0);
        this.head.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.head.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 5, 0.0F);
        this.tail = new RendererModel(this, 20, 20);
        this.tail.setRotationPoint(0.0F, 11.0F, 3.0F);
        this.tail.addBox(-1.5F, -1.0F, 0.0F, 3, 3, 1, 0.0F);
        this.setRotateAngle(tail, 1.2566370614359172F, 0.0F, 0.0F);
        this.flipperRight = new RendererModel(this, 20, 10);
        this.flipperRight.setRotationPoint(-2.5F, 1.0F, 0.0F);
        this.flipperRight.addBox(-1.0F, 0.0F, -1.0F, 1, 7, 3, 0.0F);
        this.setRotateAngle(flipperRight, 0.0F, 0.0F, 0.08726646259971647F);
        this.feetLeft = new RendererModel(this, 0, 25);
        this.feetLeft.mirror = true;
        this.feetLeft.setRotationPoint(1.0F, 11.0F, 0.0F);
        this.feetLeft.addBox(0.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(feetLeft, 0.0F, -0.2617993877991494F, 0.0F);
        this.flipperLeft = new RendererModel(this, 20, 10);
        this.flipperLeft.mirror = true;
        this.flipperLeft.setRotationPoint(2.5F, 1.0F, 0.0F);
        this.flipperLeft.addBox(0.0F, 0.0F, -1.0F, 1, 7, 3, 0.0F);
        this.setRotateAngle(flipperLeft, 0.0F, 0.0F, -0.08726646259971647F);
        this.head.addChild(this.beak);
        this.body.addChild(this.feetRight);
        this.body.addChild(this.tail);
        this.body.addChild(this.flipperRight);
        this.body.addChild(this.feetLeft);
        this.body.addChild(this.flipperLeft);
    }

    @Override
    public void func_78088_a(@Nonnull EntityAdeliePenguin penguin, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
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

    private void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void func_212844_a_(@Nonnull EntityAdeliePenguin penguin, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleZ = (MathHelper.cos(limbSwing * 1.3324F) * 1.4F * limbSwingAmount) / 6;
        this.body.rotateAngleZ = (MathHelper.cos(limbSwing * 1.3324F) * 1.4F * limbSwingAmount) / 6;
        this.feetRight.rotateAngleX = MathHelper.cos(limbSwing * 1.3324F) * 1.2F * limbSwingAmount;
        this.feetLeft.rotateAngleX = MathHelper.cos(limbSwing * 1.3324F + (float) Math.PI) * 1.2F * limbSwingAmount;
        this.flipperRight.rotateAngleZ = 0.08726646259971647F + (MathHelper.cos((float) penguin.rotationFlipper) * limbSwingAmount);
        this.flipperLeft.rotateAngleZ = -0.08726646259971647F + (MathHelper.cos((float) penguin.rotationFlipper + (float) Math.PI) * limbSwingAmount);
        this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * 1.4F * limbSwingAmount;
    }
}