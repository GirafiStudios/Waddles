package com.girafi.waddles.client;

import com.girafi.waddles.client.model.PenguinModel;
import com.girafi.waddles.client.renderer.PenguinRenderer;
import com.girafi.waddles.init.PenguinRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class ClientHandler implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRenderers.register(PenguinRegistry.ADELIE_PENGUIN.get(), PenguinRenderer::new);
        ModelLayerRegistry.registerModelLayer(ClientHelper.PENGUIN_LAYER, PenguinModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(ClientHelper.PENGUIN_LAYER_BABY, () -> PenguinModel.createBodyLayer().apply(PenguinModel.BABY_TRANSFORMER));
    }
}