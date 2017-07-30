package com.girafi.waddles.client.renderer;

import com.girafi.waddles.client.model.ModelPenguin;
import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.girafi.waddles.utils.Reference;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class RenderPenguin extends RenderLiving<EntityAdeliePenguin> {

    public RenderPenguin(RenderManager renderManager) {
        super(renderManager, new ModelPenguin(), 0.5F);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityAdeliePenguin penguin) {
        String name = penguin.getName().toLowerCase().trim();
        if (name.equals("joshie") || name.equals("joshiejack")) {
            return this.getPenguinTexture("joshie");
        } else if (name.equals("darkosto")) {
            return this.getPenguinTexture("darkosto");
        }
        return penguin.isChild() ? this.getPenguinTexture("adelie_child") : this.getPenguinTexture("adelie");
    }

    private ResourceLocation getPenguinTexture(String fileName) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/penguin/" + fileName + ".png");
    }
}