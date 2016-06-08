package com.girafi.waddles.client.renderer;

import com.girafi.waddles.Reference;
import com.girafi.waddles.client.model.ModelPenguin;
import com.girafi.waddles.entity.EntityPenguin;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPenguin extends RenderLiving<EntityPenguin> {
    private static final ResourceLocation PENGUIN_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/entity/penguin.png");

    public RenderPenguin(RenderManager renderManager) {
        super(renderManager, new ModelPenguin(), 0.6F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPenguin entityPenguin) {
        return PENGUIN_TEXTURE;
    }
}