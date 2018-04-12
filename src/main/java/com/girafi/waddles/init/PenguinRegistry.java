package com.girafi.waddles.init;

import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.girafi.waddles.utils.ConfigurationHandler;
import com.girafi.waddles.utils.Reference;
import com.google.common.base.CaseFormat;
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

    public static final EntityEntry ADELIE_PENGUIN = createEntity(EntityAdeliePenguin.class, 0x000000, 0xFFFFFF, 2, 1, 4, new BiomeDictionary.Type[]{SNOWY}, new BiomeDictionary.Type[]{FOREST});

    private static EntityEntry createEntity(Class<? extends Entity> entityClass, int eggPrimary, int eggSecondary, int weight, int min, int max, BiomeDictionary.Type[] typesInclude, BiomeDictionary.Type[] typesExclude) {
        List<Biome> spawnable_biomes = Lists.newArrayList();

        String subCategoryNames = ConfigurationHandler.CATEGORY_PENGUIN_SPAWNS + Configuration.CATEGORY_SPLITTER + "adelie_penguin" + Configuration.CATEGORY_SPLITTER + "Spawnable Biomes";
        String[] include = ConfigurationHandler.config.getStringList("Include", subCategoryNames, toStringArray(typesInclude), "BiomeDictionary types to include");
        String[] exclude = ConfigurationHandler.config.getStringList("Exclude", subCategoryNames, toStringArray(typesExclude), "BiomeDictionary types to exclude");

        ConfigurationHandler.config.save();

        for (Biome biome : Biome.REGISTRY) {
            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);
            if (types.containsAll(Arrays.asList(include)) && !types.containsAll(Arrays.asList(exclude)) && !types.contains(NETHER) && !biome.getSpawnableList(EnumCreatureType.CREATURE).isEmpty()) {
                spawnable_biomes.add(biome);
            }
        }
        return createEntity(entityClass, eggPrimary, eggSecondary, weight, min, max, spawnable_biomes);
    }

    private static EntityEntry createEntity(Class<? extends Entity> entityClass, int eggPrimary, int eggSecondary, int weight, int min, int max, Biome... biomes) {
        return createEntity(entityClass, eggPrimary, eggSecondary, weight, min, max, Arrays.asList(biomes));
    }

    private static EntityEntry createEntity(Class<? extends Entity> entityClass, int eggPrimary, int eggSecondary, int weight, int min, int max, Iterable<Biome> biomes) {
        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityClass.getSimpleName()).replace("entity_", ""));
        EntityEntry entry = new EntityEntry(entityClass, location.toString());
        entry.setRegistryName(location);
        entry.setEgg(new EntityList.EntityEggInfo(location, eggPrimary, eggSecondary));
        PenguinRegistry.biomes = biomes;
        entities.add(entry);

        String subCategoryNames = ConfigurationHandler.CATEGORY_PENGUIN_SPAWNS + Configuration.CATEGORY_SPLITTER + entry.getRegistryName().getResourcePath();
        PenguinRegistry.weight = ConfigurationHandler.config.get(subCategoryNames, "Weight", weight).getInt();
        PenguinRegistry.minCount = ConfigurationHandler.config.get(subCategoryNames, "Min", min).getInt();
        PenguinRegistry.maxCount = ConfigurationHandler.config.get(subCategoryNames, "Max", max).getInt();

        ConfigurationHandler.config.save();

        return entry;
    }

    private static String[] toStringArray(BiomeDictionary.Type[] types) {
        String[] def = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            BiomeDictionary.Type type = types[i];
            def[i] = type.getName();
        }
        return def;
    }

    @SubscribeEvent
    public static void registerPenguins(RegistryEvent.Register<EntityEntry> event) {
        int networkId = 0;
        for (EntityEntry entry : entities) {
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