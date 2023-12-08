package com.girafi.waddles.client;

import com.girafi.waddles.Constants;
import com.girafi.waddles.client.model.PenguinModel;
import com.girafi.waddles.client.renderer.PenguinRenderer;
import com.girafi.waddles.init.PenguinRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientHandler {

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(PenguinRegistry.ADELIE_PENGUIN.get(), PenguinRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ClientHelper.PENGUIN_LAYER, PenguinModel::createBodyLayer);
    }
}