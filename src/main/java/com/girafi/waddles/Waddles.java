package com.girafi.waddles;

import com.girafi.waddles.client.renderer.RenderPenguin;
import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = Waddles.MOD_ID)
public class Waddles {
    public static final String MOD_ID = "waddles";
    public static final ResourceLocation LOOT_ENTITIES_PENGUIN_FISH = LootTableList.register(new ResourceLocation(MOD_ID, "entities/penguin"));

    public Waddles() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec);
    }

    public void setupCommon(final FMLCommonSetupEvent event) {
        PenguinRegistry.addSpawn();
    }

    public void setupClient(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityAdeliePenguin.class, RenderPenguin::new);
    }
}