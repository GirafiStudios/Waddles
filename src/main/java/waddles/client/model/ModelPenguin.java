package waddles.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelPenguin extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer beak;
    public ModelRenderer wing1;
    public ModelRenderer wing2;
    public ModelRenderer leg2;
    public ModelRenderer leg1;
    public ModelRenderer tail;

    public ModelPenguin() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.body = new ModelRenderer(this, 0, 9);
        this.body.setRotationPoint(0.0F, 12.0F, 1.0F);
        this.body.addBox(-2.5F, 0.0F, -2.0F, 5, 11, 5, 0.0F);
        this.wing2 = new ModelRenderer(this, 20, 10);
        this.wing2.mirror = true;
        this.wing2.setRotationPoint(2.5F, 1.0F, 0.0F);
        this.wing2.addBox(0.0F, 0.0F, -1.0F, 1, 7, 3, 0.0F);
        this.setRotateAngle(wing2, 0.0F, 0.0F, -0.08726646259971647F);
        this.leg1 = new ModelRenderer(this, 0, 25);
        this.leg1.setRotationPoint(-1.0F, 11.0F, 0.0F);
        this.leg1.addBox(-2.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(leg1, 0.0F, 0.2617993877991494F, 0.0F);
        this.beak = new ModelRenderer(this, 18, 0);
        this.beak.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.beak.addBox(-0.5F, -3.0F, -4.0F, 1, 2, 3, 0.0F);
        this.setRotateAngle(beak, 0.08726646259971647F, -0.0F, 0.0F);
        this.wing1 = new ModelRenderer(this, 20, 10);
        this.wing1.setRotationPoint(-2.5F, 1.0F, 0.0F);
        this.wing1.addBox(-1.0F, 0.0F, -1.0F, 1, 7, 3, 0.0F);
        this.setRotateAngle(wing1, 0.0F, 0.0F, 0.08726646259971647F);
        this.tail = new ModelRenderer(this, 20, 20);
        this.tail.setRotationPoint(0.0F, 11.0F, 3.0F);
        this.tail.addBox(-1.5F, -1.0F, 0.0F, 3, 3, 1, 0.0F);
        this.setRotateAngle(tail, 1.2566370614359172F, 0.0F, 0.0F);
        this.leg2 = new ModelRenderer(this, 0, 25);
        this.leg2.setRotationPoint(1.0F, 11.0F, 0.0F);
        this.leg2.addBox(0.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F);
        this.setRotateAngle(leg2, 0.0F, -0.2617993877991494F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.head.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 5, 0.0F);
        this.body.addChild(this.wing2);
        this.body.addChild(this.leg1);
        this.head.addChild(this.beak);
        this.body.addChild(this.wing1);
        this.body.addChild(this.tail);
        this.body.addChild(this.leg2);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.body.render(scale);
        this.head.render(scale);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}