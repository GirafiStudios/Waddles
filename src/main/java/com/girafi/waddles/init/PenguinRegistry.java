package com.girafi.waddles.init;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.entity.AdeliePenguinEntity;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.List;

@EventBusSubscriber(modid = Waddles.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class PenguinRegistry {
    private static final List<EntityType<?>> ENTITIES = Lists.newArrayList();
    private static final List<Item> SPAWN_EGGS = Lists.newArrayList();

    public static final EntityType<AdeliePenguinEntity> ADELIE_PENGUIN = createEntity("adelie_penguin", AdeliePenguinEntity::new, 0.4F, 0.95F, 0x000000, 0xFFFFFF);

    private static <T extends AnimalEntity> EntityType<T> createEntity(String name, EntityType.IFactory<T> factory, float width, float height, int eggPrimary, int eggSecondary) {
        ResourceLocation location = new ResourceLocation(Waddles.MOD_ID, name);
        EntityType<T> entity = EntityType.Builder.create(factory, EntityClassification.CREATURE).size(width, height).setTrackingRange(64).setUpdateInterval(1).build(location.toString());
        entity.setRegistryName(location);
        ENTITIES.add(entity);
        Item spawnEgg = new SpawnEggItem(entity, eggPrimary, eggSecondary, (new Item.Properties()).group(ItemGroup.MISC));
        spawnEgg.setRegistryName(new ResourceLocation(Waddles.MOD_ID, name + "_spawn_egg"));
        SPAWN_EGGS.add(spawnEgg);

        return entity;
    }

    @SubscribeEvent
    public static void registerPenguins(RegistryEvent.Register<EntityType<?>> event) {
        for (EntityType entity : ENTITIES) {
            Preconditions.checkNotNull(entity.getRegistryName(), "registryName");
            event.getRegistry().register(entity);
            EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AdeliePenguinEntity::canPenguinSpawn);
        }
        GlobalEntityTypeAttributes.put(ADELIE_PENGUIN, AdeliePenguinEntity.getAttributes().create());
    }

    @SubscribeEvent
    public static void registerSpawnEggs(RegistryEvent.Register<Item> event) {
        for (Item spawnEgg : SPAWN_EGGS) {
            Preconditions.checkNotNull(spawnEgg.getRegistryName(), "registryName");
            event.getRegistry().register(spawnEgg);
        }
    }
}