package com.girafi.waddles.init;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.utils.BiomeDictionaryHelper;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Waddles.MOD_ID)
public class PenguinSpawn {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addSpawn(BiomeLoadingEvent event) {
        if (event.getName() != null) {
            Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
            if (biome != null) {
                ResourceKey<Biome> resourceKey = ResourceKey.create(ForgeRegistries.Keys.BIOMES, event.getName());
                List<BiomeDictionary.Type> includeList = Arrays.asList(BiomeDictionaryHelper.toBiomeTypeArray(ConfigurationHandler.SPAWN.include.get()));
                List<BiomeDictionary.Type> excludeList = Arrays.asList(BiomeDictionaryHelper.toBiomeTypeArray(ConfigurationHandler.SPAWN.exclude.get()));
                if (!includeList.isEmpty()) {
                    Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(resourceKey);
                    if (biomeTypes.stream().noneMatch(excludeList::contains) && biomeTypes.stream().anyMatch(includeList::contains)) {
                        event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(PenguinRegistry.ADELIE_PENGUIN.get(), ConfigurationHandler.SPAWN.weight.get(), ConfigurationHandler.SPAWN.min.get(), ConfigurationHandler.SPAWN.max.get()));
                    }
                } else {
                    throw new IllegalArgumentException("Do not leave the BiomeDictionary type inclusion list empty. If you wish to disable spawning of an entity, set the weight to 0 instead.");
                }
            }
        }
    }
}