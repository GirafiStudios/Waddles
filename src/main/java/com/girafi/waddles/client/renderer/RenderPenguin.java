package com.girafi.waddles.client.renderer;

import com.girafi.waddles.client.model.ModelPenguin;
import com.girafi.waddles.entity.EntityPenguin;
import com.girafi.waddles.utils.Reference;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPenguin extends RenderLiving<EntityPenguin> {
    private static final ResourceLocation CHILD_ADELIE_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/entity/penguinAdelieChild.png");
    private static final ResourceLocation ADELIE_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/entity/penguinAdelie.png");

    public RenderPenguin(RenderManager renderManager) {
        super(renderManager, new ModelPenguin(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPenguin entityPenguin) {
        return entityPenguin.isChild() ? CHILD_ADELIE_TEXTURE : ADELIE_TEXTURE;
    }
}