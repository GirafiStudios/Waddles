package com.girafi.waddles.client;

import com.girafi.waddles.client.model.PenguinModel;
import com.girafi.waddles.client.renderer.PenguinRenderer;
import com.girafi.waddles.init.PenguinRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ClientHandler implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(PenguinRegistry.ADELIE_PENGUIN.get(), PenguinRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ClientHelper.PENGUIN_LAYER, PenguinModel::createBodyLayer);
    }
}