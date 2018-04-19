package com.girafi.waddles.init;

import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.girafi.waddles.utils.BiomeDictionaryHelper;
import com.girafi.waddles.utils.ConfigurationHandler;
import com.girafi.waddles.utils.Reference;
import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@EventBusSubscriber
public class PenguinRegistry {
    private static List<EntityEntry> entities = Lists.newArrayList();
    private static Iterable<Biome> biomes = Lists.newArrayList();
    private static int minCount;
    private static int maxCount;
    private static int weight;

    public static final EntityEntry ADELIE_PENGUIN = createEntity(EntityAdeliePenguin.class, 0x000000, 0xFFFFFF, 2, 1, 4, new BiomeDictionary.Type[]{SNOWY}, new BiomeDictionary.Type[]{FOREST, MOUNTAIN, NETHER});

    private static EntityEntry createEntity(Class<? extends Entity> entityClass, int eggPrimary, int eggSecondary, int weight, int min, int max, BiomeDictionary.Type[] typesInclude, BiomeDictionary.Type[] typesExclude) {
        List<Biome> spawnable_biomes = Lists.newArrayList();

        String subCategoryNames = ConfigurationHandler.CATEGORY_PENGUIN_SPAWNS + Configuration.CATEGORY_SPLITTER + classToRegistryName(entityClass).getResourcePath() + Configuration.CATEGORY_SPLITTER + "Spawnable Biomes";
        String[] include = ConfigurationHandler.config.getStringList("Include", subCategoryNames, BiomeDictionaryHelper.toStringArray(typesInclude), "BiomeDictionary types to include");
        String[] exclude = ConfigurationHandler.config.getStringList("Exclude", subCategoryNames, BiomeDictionaryHelper.toStringArray(typesExclude), "BiomeDictionary types to exclude");
        ConfigurationHandler.config.save();

        List<BiomeDictionary.Type> biomeTypes = new ArrayList<>(BiomeDictionary.Type.getAll());

        for (String in : include) {
            if (!biomeTypes.contains(BiomeDictionaryHelper.getType(in))) {
                throw new IllegalArgumentException("Waddles could not find BiomeDictionary Type '" + in + "' to include, please consult the config file");
            }
        }
        for (String ex : exclude) {
            if (!biomeTypes.contains(BiomeDictionaryHelper.getType(ex))) {
                throw new IllegalArgumentException("Waddles could not find BiomeDictionary Type '" + ex + "' to exclude, please consult the config file");
            }
        }

        List<BiomeDictionary.Type> includeList = new ArrayList<>(Arrays.asList(BiomeDictionaryHelper.toBiomeTypeArray(include)));
        List<BiomeDictionary.Type> excludeList = new ArrayList<>(Arrays.asList(BiomeDictionaryHelper.toBiomeTypeArray(exclude)));
        if (!includeList.isEmpty()) {
            for (BiomeDictionary.Type type : includeList) {
                for (Biome biome : BiomeDictionary.getBiomes(type)) {
                    if (!biome.getSpawnableList(EnumCreatureType.CREATURE).isEmpty()) {
                        spawnable_biomes.add(biome);
                    }
                }
            }
            if (!excludeList.isEmpty()) {
                for (BiomeDictionary.Type type : excludeList) {
                    Set<Biome> excludeBiomes = BiomeDictionary.getBiomes(type);
                    for (Biome biome : excludeBiomes) {
                        spawnable_biomes.remove(biome);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Do not leave the list of biomes to include empty. If you wish to disable spawning of an entity, set the weight to 0 instead.");
        }
        return createEntity(entityClass, eggPrimary, eggSecondary, weight, min, max, spawnable_biomes);
    }

    private static EntityEntry createEntity(Class<? extends Entity> entityClass, int eggPrimary, int eggSecondary, int weight, int min, int max, Biome... biomes) {
        return createEntity(entityClass, eggPrimary, eggSecondary, weight, min, max, Arrays.asList(biomes));
    }

    private static EntityEntry createEntity(Class<? extends Entity> entityClass, int eggPrimary, int eggSecondary, int weight, int min, int max, Iterable<Biome> biomes) {
        ResourceLocation location = classToRegistryName(entityClass);
        EntityEntry entry = new EntityEntry(entityClass, location.toString());
        entry.setRegistryName(location);
        entry.setEgg(new EntityList.EntityEggInfo(location, eggPrimary, eggSecondary));
        PenguinRegistry.biomes = biomes;
        entities.add(entry);

        String subCategoryNames = ConfigurationHandler.CATEGORY_PENGUIN_SPAWNS + Configuration.CATEGORY_SPLITTER + location.getResourcePath();
        PenguinRegistry.weight = ConfigurationHandler.config.get(subCategoryNames, "Weight", weight).getInt();
        PenguinRegistry.minCount = ConfigurationHandler.config.get(subCategoryNames, "Min", min).getInt();
        PenguinRegistry.maxCount = ConfigurationHandler.config.get(subCategoryNames, "Max", max).getInt();

        ConfigurationHandler.config.save();

        return entry;
    }

    private static ResourceLocation classToRegistryName(Class<? extends Entity> entityClass) {
        return new ResourceLocation(Reference.MOD_ID, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityClass.getSimpleName()).replace("entity_", ""));
    }

    @SubscribeEvent
    public static void registerPenguins(RegistryEvent.Register<EntityEntry> event) {
        int networkId = 0;
        for (EntityEntry entry : entities) {
            Preconditions.checkNotNull(entry.getRegistryName(), "registryName");
            networkId++;
            event.getRegistry().register(EntityEntryBuilder.create()
                    .entity(entry.getEntityClass())
                    .id(entry.getRegistryName(), networkId)
                    .name(entry.getName())
                    .tracker(64, 1, true)
                    .egg(entry.getEgg().primaryColor, entry.getEgg().secondaryColor)
                    .spawn(EnumCreatureType.CREATURE, weight, minCount, maxCount, biomes)
                    .build());
        }
    }
}