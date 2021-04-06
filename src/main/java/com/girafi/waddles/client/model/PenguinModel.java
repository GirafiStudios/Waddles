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
        this.texWidth = 32;
        this.texHeight = 32;
        this.beak = new ModelRenderer(this, 18, 0);
        this.beak.setPos(0.0F, 0.0F, 0.0F);
        this.beak.addBox(-0.5F, -3.0F, -4.0F, 1, 2, 3, 0.0F);
        this.setRotateAngle(beak, 0.08726646259971647F, -0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 9);
        this.body.setPos(0.0F, 12.0F, 1.0F);
        this.body.addBox(-2.5F, 0.0F, -2.0F, 5, 11, 5, 0.0F);
        this.feetRight = new ModelRenderer(this, 0, 25);
        this.feetRight.setPos(-1.0F, 11.0F, 0.0F);
        this.feetRight.addBox(-2.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(feetRight, 0.0F, 0.2617993877991494F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setPos(0.0F, 12.0F, 0.0F);
        this.head.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 5, 0.0F);
        this.tail = new ModelRenderer(this, 20, 20);
        this.tail.setPos(0.0F, 11.0F, 3.0F);
        this.tail.addBox(-1.5F, -1.0F, 0.0F, 3, 3, 1, 0.0F);
        this.setRotateAngle(tail, 1.2566370614359172F, 0.0F, 0.0F);
        this.flipperRight = new ModelRenderer(this, 20, 10);
        this.flipperRight.setPos(-2.5F, 1.0F, 0.0F);
        this.flipperRight.addBox(-1.0F, 0.0F, -1.0F, 1, 7, 3, 0.0F);
        this.setRotateAngle(flipperRight, 0.0F, 0.0F, 0.08726646259971647F);
        this.feetLeft = new ModelRenderer(this, 0, 25);
        this.feetLeft.mirror = true;
        this.feetLeft.setPos(1.0F, 11.0F, 0.0F);
        this.feetLeft.addBox(0.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(feetLeft, 0.0F, -0.2617993877991494F, 0.0F);
        this.flipperLeft = new ModelRenderer(this, 20, 10);
        this.flipperLeft.mirror = true;
        this.flipperLeft.setPos(2.5F, 1.0F, 0.0F);
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
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    @Nonnull
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body);
    }

    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void setupAnim(@Nonnull AdeliePenguinEntity penguin, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * 0.017453292F;
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.head.zRot = (MathHelper.cos(limbSwing * 1.3324F) * 1.4F * limbSwingAmount) / 6;
        this.body.zRot = (MathHelper.cos(limbSwing * 1.3324F) * 1.4F * limbSwingAmount) / 6;
        this.feetRight.xRot = MathHelper.cos(limbSwing * 1.3324F) * 1.2F * limbSwingAmount;
        this.feetLeft.xRot = MathHelper.cos(limbSwing * 1.3324F + (float) Math.PI) * 1.2F * limbSwingAmount;
        this.flipperRight.zRot = 0.08726646259971647F + (MathHelper.cos(penguin.rotationFlipper) * limbSwingAmount);
        this.flipperLeft.zRot = -0.08726646259971647F + (MathHelper.cos((float) penguin.rotationFlipper + (float) Math.PI) * limbSwingAmount);
        this.tail.yRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * 1.4F * limbSwingAmount;
    }
}