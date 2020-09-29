package com.girafi.waddles.init;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Waddles.MOD_ID)
public class PenguinSpawn {

    @SubscribeEvent(priority =  EventPriority.HIGH)
    public static void addSpawn(BiomeLoadingEvent event) {
        Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
        if (biome != null && (ConfigurationHandler.SPAWN.biomeCategoryBiomes.get().contains(biome.getCategory().getName()) || ConfigurationHandler.SPAWN.additionalBiomes.get().contains(event.getName().toString()))) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(PenguinRegistry.ADELIE_PENGUIN, ConfigurationHandler.SPAWN.weight.get(), ConfigurationHandler.SPAWN.min.get(), ConfigurationHandler.SPAWN.max.get()));
        }
    }
}