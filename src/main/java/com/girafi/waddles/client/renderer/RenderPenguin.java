package com.girafi.waddles.client.renderer;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.client.model.ModelPenguin;
import com.girafi.waddles.entity.EntityAdeliePenguin;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class RenderPenguin extends RenderLiving<EntityAdeliePenguin> {

    public RenderPenguin(RenderManager renderManager) {
        super(renderManager, new ModelPenguin(), 0.5F);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityAdeliePenguin penguin) {
        String name = penguin.getName().getString().toLowerCase().trim();
        if (name.equals("joshie") || name.equals("joshiejack")) {
            return this.getPenguinTexture("joshie");
        } else if (name.equals("darkosto")) {
            return this.getPenguinTexture("darkosto");
        }
        return penguin.isChild() ? this.getPenguinTexture("adelie_child") : this.getPenguinTexture("adelie");
    }

    private ResourceLocation getPenguinTexture(String fileName) {
        return new ResourceLocation(Waddles.MOD_ID, "textures/entity/penguin/" + fileName + ".png");
    }
}