package com.girafi.waddles.client.renderer;

import com.girafi.waddles.Constants;
import com.girafi.waddles.client.ClientHelper;
import com.girafi.waddles.client.model.PenguinModel;
import com.girafi.waddles.entity.AdeliePenguinEntity;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class PenguinRenderer extends AgeableMobRenderer<AdeliePenguinEntity, PenguinRenderState, PenguinModel> {

    public PenguinRenderer(EntityRendererProvider.Context context) {
        super(context, new PenguinModel(context.bakeLayer(ClientHelper.PENGUIN_LAYER)), new PenguinModel(context.bakeLayer(ClientHelper.PENGUIN_LAYER_BABY)), 0.5F);
    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull PenguinRenderState state) {
        String name = state.customName.getString().toLowerCase().trim(); //TODO Test
        return switch (name) {
            case "joshie", "joshiejack" -> this.getPenguinTexture("joshie");
            case "darkosto" -> this.getPenguinTexture("darkosto");
            case "waddles", "adelie", "girafi", "wiiv" -> getDefault(state);
            default -> ConfigurationHandler.GENERAL.darkostoDefault.get() ? this.getPenguinTexture("darkosto") : getDefault(state);
        };
    }

    private ResourceLocation getDefault(@Nonnull PenguinRenderState state) {
        return state.isBaby ? this.getPenguinTexture("adelie_child") : this.getPenguinTexture("adelie");
    }

    private ResourceLocation getPenguinTexture(String fileName) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/penguin/" + fileName + ".png");
    }

    @Override
    @Nonnull
    public PenguinRenderState createRenderState() {
        return new PenguinRenderState();
    }

    @Override
    public void extractRenderState(@Nonnull AdeliePenguinEntity penguin, @Nonnull PenguinRenderState state, float partialTick) {
        super.extractRenderState(penguin, state, partialTick);
        state.rotationFlipper = penguin.rotationFlipper;
    }
}