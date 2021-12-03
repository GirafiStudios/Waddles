package com.girafi.waddles.client.renderer;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.client.ClientHandler;
import com.girafi.waddles.client.model.PenguinModel;
import com.girafi.waddles.entity.AdeliePenguinEntity;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class PenguinRenderer extends MobRenderer<AdeliePenguinEntity, PenguinModel<AdeliePenguinEntity>> {

    public PenguinRenderer(EntityRendererProvider.Context context) {
        super(context, new PenguinModel<>(context.bakeLayer(ClientHandler.PENGUIN_LAYER)), 0.5F);
    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull AdeliePenguinEntity penguin) {
        String name = penguin.getName().getString().toLowerCase().trim();
        return switch (name) {
            case "joshie", "joshiejack" -> this.getPenguinTexture("joshie");
            case "darkosto" -> this.getPenguinTexture("darkosto");
            case "waddles", "adelie", "girafi", "wiiv" -> getDefault(penguin);
            default -> ConfigurationHandler.GENERAL.darkostoDefault.get() ? this.getPenguinTexture("darkosto") : getDefault(penguin);
        };
    }

    private ResourceLocation getDefault(@Nonnull AdeliePenguinEntity penguin) {
        return penguin.isBaby() ? this.getPenguinTexture("adelie_child") : this.getPenguinTexture("adelie");
    }

    private ResourceLocation getPenguinTexture(String fileName) {
        return new ResourceLocation(Waddles.MOD_ID, "textures/entity/penguin/" + fileName + ".png");
    }
}