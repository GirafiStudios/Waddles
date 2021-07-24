package com.girafi.waddles;

import com.girafi.waddles.client.model.PenguinModel;
import com.girafi.waddles.client.renderer.PenguinRenderer;
import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.init.WaddlesSounds;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlclient.registry.RenderingRegistry;

@Mod(value = Waddles.MOD_ID)
public class Waddles {
    public static final String MOD_ID = "waddles";
    public static ModelLayerLocation PENGUIN_LAYER = new ModelLayerLocation(new ResourceLocation(MOD_ID, "penguin"), "penguin");

    public Waddles() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec);
        registerDeferredRegistries(eventBus);
    }

    public void setupCommon(final FMLCommonSetupEvent event) {

    }

    public void setupClient(final FMLClientSetupEvent event) {
        //RenderingRegistry.registerEntityRenderingHandler(PenguinRegistry.ADELIE_PENGUIN.get(), PenguinRenderer::new);
        EntityRenderers.register(PenguinRegistry.ADELIE_PENGUIN.get(), PenguinRenderer::new); //TODO Temporary workaround, as the Forge way is broken
        RenderingRegistry.registerLayerDefinition(PENGUIN_LAYER, PenguinModel::createBodyLayer);
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        PenguinRegistry.ENTITY_DEFERRED.register(modBus);
        WaddlesSounds.SOUND_EVENT_DEFERRED.register(modBus);
    }
}