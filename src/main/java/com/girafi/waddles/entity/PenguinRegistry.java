package com.girafi.waddles.entity;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.utils.ConfigurationHandler;
import com.girafi.waddles.utils.Reference;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.List;

@EventBusSubscriber
public class PenguinRegistry {
    private static List<EntityEntry> entities = Lists.newArrayList();
    public static final EntityEntry ADELIE_PENGUIN = createEntity(EntityAdeliePenguin.class, "adelie_penguin", 0x000000, 0xFFFFFF);

    private static EntityEntry createEntity(Class<? extends Entity> entityClass, String name, int eggPrimary, int eggSecondary) {
        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, name);
        EntityEntry entry = new EntityEntry(entityClass, location.toString());
        entry.setRegistryName(location);
        entry.setEgg(new EntityList.EntityEggInfo(location, eggPrimary, eggSecondary));
        entities.add(entry);
        return entry;
    }

    @SubscribeEvent
    public static void registerPenguins(RegistryEvent.Register<EntityEntry> event) {
        int id = 0;
        for (EntityEntry entry : entities) {
            event.getRegistry().register(entry);
            id++;
            EntityRegistry.registerModEntity(entry.getRegistryName(), entry.getEntityClass(), entry.getName(), id, Waddles.instance, 64, 1, true);
        }
    }

    public static void addPenguinSpawn(Class<? extends EntityAgeable> penguinClass, EntityEntry penguinEntry, int defaultWeight, int defaultMin, int defaultMax, Biome... biomes) {
        String subCategoryNames = ConfigurationHandler.CATEGORY_PENGUIN_SPAWNS + Configuration.CATEGORY_SPLITTER + penguinEntry.getRegistryName().getResourcePath();
        int weight = ConfigurationHandler.config.get(subCategoryNames, "Weight", defaultWeight).getInt();
        int min = ConfigurationHandler.config.get(subCategoryNames, "Min", defaultMin).getInt();
        int max = ConfigurationHandler.config.get(subCategoryNames, "Max", defaultMax).getInt();

        ConfigurationHandler.config.save();

        if (weight != 0) {
            EntityRegistry.addSpawn(penguinClass, weight, min, max, EnumCreatureType.CREATURE, biomes);
        }
    }
}