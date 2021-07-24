package com.girafi.waddles.client;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.client.model.PenguinModel;
import com.girafi.waddles.client.renderer.PenguinRenderer;
import com.girafi.waddles.init.PenguinRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmlclient.registry.RenderingRegistry;

public class ClientHandler {
    public static ModelLayerLocation PENGUIN_LAYER = new ModelLayerLocation(new ResourceLocation(Waddles.MOD_ID, "penguin"), "penguin");

    public static void init() {
        //RenderingRegistry.registerEntityRenderingHandler(PenguinRegistry.ADELIE_PENGUIN.get(), PenguinRenderer::new);
        EntityRenderers.register(PenguinRegistry.ADELIE_PENGUIN.get(), PenguinRenderer::new); //TODO Temporary workaround, as the Forge way is broken
        RenderingRegistry.registerLayerDefinition(PENGUIN_LAYER, PenguinModel::createBodyLayer);
    }
}