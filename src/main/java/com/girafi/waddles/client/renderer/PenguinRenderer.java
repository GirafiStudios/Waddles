package com.girafi.waddles.client.renderer;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.client.model.PenguinModel;
import com.girafi.waddles.entity.AdeliePenguinEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class PenguinRenderer extends MobRenderer<AdeliePenguinEntity, PenguinModel<AdeliePenguinEntity>> {

    public PenguinRenderer(EntityRendererProvider.Context context) {
        super(context, new PenguinModel<>(context.bakeLayer(Waddles.PENGUIN_LAYER)), 0.5F);
    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull AdeliePenguinEntity penguin) {
        String name = penguin.getName().getString().toLowerCase().trim();
        if (name.equals("joshie") || name.equals("joshiejack")) {
            return this.getPenguinTexture("joshie");
        } else if (name.equals("darkosto")) {
            return this.getPenguinTexture("darkosto");
        }
        return penguin.isBaby() ? this.getPenguinTexture("adelie_child") : this.getPenguinTexture("adelie");
    }

    private ResourceLocation getPenguinTexture(String fileName) {
        return new ResourceLocation(Waddles.MOD_ID, "textures/entity/penguin/" + fileName + ".png");
    }
}