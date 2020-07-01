package com.girafi.waddles.client.model;

import com.girafi.waddles.entity.AdeliePenguinEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class PenguinModel<T extends AdeliePenguinEntity> extends AgeableModel<T> {
    private ModelRenderer head;
    private ModelRenderer body;
    private ModelRenderer beak;
    private ModelRenderer flipperRight;
    private ModelRenderer flipperLeft;
    private ModelRenderer feetLeft;
    private ModelRenderer feetRight;
    private ModelRenderer tail;

    public PenguinModel() {
        super(false, 6.0F, 0.0F);
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.beak = new ModelRenderer(this, 18, 0);
        this.beak.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.beak.addBox(-0.5F, -3.0F, -4.0F, 1, 2, 3, 0.0F);
        this.setRotateAngle(beak, 0.08726646259971647F, -0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 9);
        this.body.setRotationPoint(0.0F, 12.0F, 1.0F);
        this.body.addBox(-2.5F, 0.0F, -2.0F, 5, 11, 5, 0.0F);
        this.feetRight = new ModelRenderer(this, 0, 25);
        this.feetRight.setRotationPoint(-1.0F, 11.0F, 0.0F);
        this.feetRight.addBox(-2.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(feetRight, 0.0F, 0.2617993877991494F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.head.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 5, 0.0F);
        this.tail = new ModelRenderer(this, 20, 20);
        this.tail.setRotationPoint(0.0F, 11.0F, 3.0F);
        this.tail.addBox(-1.5F, -1.0F, 0.0F, 3, 3, 1, 0.0F);
        this.setRotateAngle(tail, 1.2566370614359172F, 0.0F, 0.0F);
        this.flipperRight = new ModelRenderer(this, 20, 10);
        this.flipperRight.setRotationPoint(-2.5F, 1.0F, 0.0F);
        this.flipperRight.addBox(-1.0F, 0.0F, -1.0F, 1, 7, 3, 0.0F);
        this.setRotateAngle(flipperRight, 0.0F, 0.0F, 0.08726646259971647F);
        this.feetLeft = new ModelRenderer(this, 0, 25);
        this.feetLeft.mirror = true;
        this.feetLeft.setRotationPoint(1.0F, 11.0F, 0.0F);
        this.feetLeft.addBox(0.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(feetLeft, 0.0F, -0.2617993877991494F, 0.0F);
        this.flipperLeft = new ModelRenderer(this, 20, 10);
        this.flipperLeft.mirror = true;
        this.flipperLeft.setRotationPoint(2.5F, 1.0F, 0.0F);
        this.flipperLeft.addBox(0.0F, 0.0F, -1.0F, 1, 7, 3, 0.0F);
        this.setRotateAngle(flipperLeft, 0.0F, 0.0F, -0.08726646259971647F);
        this.head.addChild(this.beak);
        this.body.addChild(this.feetRight);
        this.body.addChild(this.feetLeft);
        this.body.addChild(this.flipperRight);
        this.body.addChild(this.flipperLeft);
        this.body.addChild(this.tail);
    }

    @Override
    @Nonnull
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    @Nonnull
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.body);
    }

    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(@Nonnull AdeliePenguinEntity penguin, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleZ = (MathHelper.cos(limbSwing * 1.3324F) * 1.4F * limbSwingAmount) / 6;
        this.body.rotateAngleZ = (MathHelper.cos(limbSwing * 1.3324F) * 1.4F * limbSwingAmount) / 6;
        this.feetRight.rotateAngleX = MathHelper.cos(limbSwing * 1.3324F) * 1.2F * limbSwingAmount;
        this.feetLeft.rotateAngleX = MathHelper.cos(limbSwing * 1.3324F + (float) Math.PI) * 1.2F * limbSwingAmount;
        this.flipperRight.rotateAngleZ = 0.08726646259971647F + (MathHelper.cos(penguin.rotationFlipper) * limbSwingAmount);
        this.flipperLeft.rotateAngleZ = -0.08726646259971647F + (MathHelper.cos((float) penguin.rotationFlipper + (float) Math.PI) * limbSwingAmount);
        this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * 1.4F * limbSwingAmount;
    }
}