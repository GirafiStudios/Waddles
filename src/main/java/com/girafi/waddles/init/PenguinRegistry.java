package com.girafi.waddles.init;

import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.girafi.waddles.utils.BiomeDictionaryHelper;
import com.girafi.waddles.utils.ConfigurationHandler;
import com.girafi.waddles.utils.Reference;
import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemSpawnEgg;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@EventBusSubscriber(modid = Reference.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class PenguinRegistry {
    private static List<EntityType> entities = Lists.newArrayList();
    private static List<Item> spawnEggs = Lists.newArrayList();
    private static Iterable<Biome> biomes = Lists.newArrayList();
    private static int minCount;
    private static int maxCount;
    private static int weight;

    public static final EntityType ADELIE_PENGUIN = createEntity(EntityAdeliePenguin.class, EntityAdeliePenguin::new, 0x000000, 0xFFFFFF, 2, 1, 4, new BiomeDictionary.Type[]{SNOWY}, new BiomeDictionary.Type[]{FOREST, MOUNTAIN, NETHER});

    private static EntityType createEntity(Class<? extends Entity> entityClass, Function<? super World, ? extends Entity> entityInstance, int eggPrimary, int eggSecondary, int weight, int min, int max, BiomeDictionary.Type[] typesInclude, BiomeDictionary.Type[] typesExclude) {
        List<Biome> spawnable_biomes = Lists.newArrayList();

        //String subCategoryNames = ConfigurationHandler.CATEGORY_PENGUIN_SPAWNS + Configuration.CATEGORY_SPLITTER + classToRegistryName(entityClass).getPath() + Configuration.CATEGORY_SPLITTER + "Spawnable Biomes";
        /*ConfigurationHandler.BUILDER.push("spawn chances");
        ConfigurationHandler.SPAWN.include = ConfigurationHandler.BUILDER.comment("include").define("Include", BiomeDictionaryHelper.toStringArray(typesInclude), o -> o instanceof String);
        ConfigurationHandler.SPAWN.exclude = ConfigurationHandler.BUILDER.comment("exclude").define("Exclude", BiomeDictionaryHelper.toStringArray(typesExclude), o -> o instanceof String);

        ConfigurationHandler.BUILDER.pop();*/
        String[] include = BiomeDictionaryHelper.toStringArray(typesInclude);
        String[] exclude = BiomeDictionaryHelper.toStringArray(typesExclude);

        validateBiomeTypes(include);
        validateBiomeTypes(exclude);

        List<BiomeDictionary.Type> includeList = Arrays.asList(BiomeDictionaryHelper.toBiomeTypeArray(include));
        List<BiomeDictionary.Type> excludeList = Arrays.asList(BiomeDictionaryHelper.toBiomeTypeArray(exclude));
        if (!includeList.isEmpty()) {
            for (BiomeDictionary.Type type : includeList) {
                for (Biome biome : BiomeDictionary.getBiomes(type)) {
                    if (!biome.getSpawns(EnumCreatureType.CREATURE).isEmpty()) {
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
            throw new IllegalArgumentException("Do not leave the BiomeDictionary type inclusion list empty. If you wish to disable spawning of an entity, set the weight to 0 instead.");
        }
        return createEntity(entityClass, entityInstance, eggPrimary, eggSecondary, weight, min, max, spawnable_biomes);
    }

    private static EntityType createEntity(Class<? extends Entity> entityClass, Function<? super World, ? extends Entity> entityInstance, int eggPrimary, int eggSecondary, int weight, int min, int max, Iterable<Biome> biomes) {
        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, classToString(entityClass));
        EntityType entity = EntityType.Builder.create(entityClass, entityInstance).build(location.toString());
        entity.setRegistryName(location);
        PenguinRegistry.biomes = biomes;
        entities.add(entity);
        Item spawnEgg = new ItemSpawnEgg(entity, eggPrimary, eggSecondary, (new Item.Builder()).group(ItemGroup.MISC));
        spawnEgg.setRegistryName(new ResourceLocation(Reference.MOD_ID, classToString(entityClass) + "_spawn_egg"));
        spawnEggs.add(spawnEgg);

        /*String subCategoryNames = ConfigurationHandler.CATEGORY_PENGUIN_SPAWNS + Configuration.CATEGORY_SPLITTER + location.getPath();
        PenguinRegistry.weight = ConfigurationHandler.config.get(subCategoryNames, "Weight", weight).getInt();
        PenguinRegistry.minCount = ConfigurationHandler.config.get(subCategoryNames, "Min", min).getInt();
        PenguinRegistry.maxCount = ConfigurationHandler.config.get(subCategoryNames, "Max", max).getInt();

        ConfigurationHandler.config.save();*/

        return entity;
    }

    private static String classToString(Class<? extends Entity> entityClass) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityClass.getSimpleName()).replace("entity_", "");
    }

    private static void validateBiomeTypes(String[] biomes) {
        for (String biome : biomes) {
            if (!BiomeDictionary.Type.getAll().contains(BiomeDictionaryHelper.getType(biome))) {
                throw new IllegalArgumentException("Waddles could not find BiomeDictionary type '" + biome + "' to include, please consult the config file");
            }
        }
    }

    @SubscribeEvent
    public static void registerPenguins(RegistryEvent.Register<EntityType<?>> event) {
        for (EntityType entity : entities) {
            Preconditions.checkNotNull(entity.getRegistryName(), "registryName");
            event.getRegistry().register(entity);
        }
    }

    @SubscribeEvent
    public static void registerSpawnEggs(RegistryEvent.Register<Item> event) {
        for (Item spawnEgg : spawnEggs) {
            Preconditions.checkNotNull(spawnEgg.getRegistryName(), "registryName");
            event.getRegistry().register(spawnEgg);
        }
    }
}