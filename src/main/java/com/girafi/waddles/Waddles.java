package com.girafi.waddles;

import com.girafi.waddles.client.renderer.RenderPenguin;
import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.girafi.waddles.utils.ConfigurationHandler;
import com.girafi.waddles.utils.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(value = Reference.MOD_ID)
public class Waddles {
    public static final ResourceLocation LOOT_ENTITIES_PENGUIN_FISH = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/penguin"));

    public Waddles() {
        FMLModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    public void setup(final FMLCommonSetupEvent event) {
        ConfigurationHandler.loadFrom(FMLPaths.CONFIGDIR.get());
    }

    public void setupClient(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityAdeliePenguin.class, RenderPenguin::new);
    }
}