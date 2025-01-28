package com.girafi.waddles.client.model;

import com.girafi.waddles.client.renderer.PenguinRenderState;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;
import java.util.Set;

public class PenguinModel extends EntityModel<PenguinRenderState> {
    public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(Set.of("head", "beak"));
    private final ModelPart head;
    private final ModelPart beak;
    private final ModelPart body;
    private final ModelPart flipperLeft;
    private final ModelPart flipperRight;
    private final ModelPart feetLeft;
    private final ModelPart feetRight;
    private final ModelPart tail;

    public PenguinModel(ModelPart part) {
        super(part);
        //super(false, 6.0F, 0.0F);
        this.head = part.getChild("head");
        this.beak = part.getChild("beak");
        this.body = part.getChild("body");
        this.flipperLeft = part.getChild("flipper_left");
        this.flipperRight = part.getChild("flipper_right");
        this.feetLeft = part.getChild("feet_left");
        this.feetRight = part.getChild("feet_right");
        this.tail = part.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelDefinition = new MeshDefinition();
        PartDefinition def = modelDefinition.getRoot();
        def.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4, 4, 5), PartPose.offset(0.0F, 12.0F, 0.0F));
        def.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(18, 0).addBox(-0.5F, -3.0F, -4.0F, 1, 2, 3), PartPose.offset(0.0F, 12.0F, 0.0F));
        def.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 9).addBox(-2.5F, 0.0F, -2.0F, 5, 11, 5), PartPose.offset(0.0F, 12.0F, 1.0F));
        def.addOrReplaceChild("flipper_left", CubeListBuilder.create().texOffs(20, 10).mirror().addBox(0.0F, 0.0F, -1.0F, 1, 7, 3), PartPose.offsetAndRotation(2.5F, 12.0F, 0.0F, 0.0F, 0.0F, -0.08726646259971647F));
        def.addOrReplaceChild("flipper_right", CubeListBuilder.create().texOffs(20, 10).addBox(-1.0F, 0.0F, -1.0F, 1, 7, 3), PartPose.offsetAndRotation(-2.5F, 12.0F, 0.0F, 0.0F, 0.0F, 0.08726646259971647F));
        def.addOrReplaceChild("feet_left", CubeListBuilder.create().texOffs(0, 25).mirror().addBox(0.0F, 0.0F, -3.0F, 2, 1, 3), PartPose.offsetAndRotation(1.0F, 23.0F, 0.0F, 0.0F, -0.2617993877991494F, 0.0F));
        def.addOrReplaceChild("feet_right", CubeListBuilder.create().texOffs(0, 25).addBox(-2.0F, 0.0F, -3.0F, 2, 1, 3), PartPose.offsetAndRotation(-1.0F, 23.0F, 0.0F, 0.0F, 0.2617993877991494F, 0.0F));
        def.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(20, 20).addBox(-1.5F, -1.0F, 0.0F, 3, 3, 1), PartPose.offsetAndRotation(0.0F, 23.0F, 3.0F, 1.2566370614359172F, 0.0F, 0.0F));
        return LayerDefinition.create(modelDefinition, 32, 32);
    }

    @Override
    public void setupAnim(@Nonnull PenguinRenderState state) {
        super.setupAnim(state);
        this.head.xRot = state.xRot * 0.017453292F;
        this.head.yRot = state.yRot * 0.017453292F;
        //this.head.zRot = (Mth.cos(limbSwing * 1.3324F) * 1.4F * limbSwingAmount) / 6;
        this.beak.xRot = this.head.xRot;
        this.beak.yRot = this.head.yRot;
        //this.body.zRot = (Mth.cos(limbSwing * 1.3324F) * 1.4F * limbSwingAmount) / 6;
        float walkSpeed = state.walkAnimationSpeed;
        float walkPos = state.walkAnimationPos;
        this.feetRight.xRot = Mth.cos(walkPos * 1.3324F) * 1.2F * walkSpeed;
        this.feetLeft.xRot = Mth.cos(walkPos * 1.3324F + (float) Math.PI) * 1.2F * walkSpeed;
        this.flipperRight.zRot = 0.08726646259971647F + (Mth.cos(state.rotationFlipper) * walkSpeed);
        this.flipperLeft.zRot = -0.08726646259971647F + (Mth.cos((float) state.rotationFlipper + (float) Math.PI) * walkSpeed);
        this.tail.yRot = Mth.cos(walkPos * 0.6662F) * 1.4F * 1.4F * walkSpeed;
    }
}