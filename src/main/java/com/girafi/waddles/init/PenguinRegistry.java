package com.girafi.waddles.init;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.entity.AdeliePenguinEntity;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@EventBusSubscriber(modid = Waddles.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class PenguinRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_DEFERRED = DeferredRegister.create(ForgeRegistries.ENTITIES, Waddles.MOD_ID);
    private static final List<Item> SPAWN_EGGS = Lists.newArrayList();

    public static final RegistryObject<EntityType<AdeliePenguinEntity>> ADELIE_PENGUIN = createEntity("adelie_penguin", AdeliePenguinEntity::new, 0.4F, 0.95F, 0x000000, 0xFFFFFF);

    private static <T extends AnimalEntity> RegistryObject<EntityType<T>> createEntity(String name, EntityType.IFactory<T> factory, float width, float height, int eggPrimary, int eggSecondary) {
        ResourceLocation location = new ResourceLocation(Waddles.MOD_ID, name);
        EntityType<T> entity = EntityType.Builder.of(factory, EntityClassification.CREATURE).sized(width, height).setTrackingRange(64).setUpdateInterval(1).build(location.toString());
        Item spawnEgg = new SpawnEggItem(entity, eggPrimary, eggSecondary, (new Item.Properties()).tab(ItemGroup.TAB_MISC));
        spawnEgg.setRegistryName(new ResourceLocation(Waddles.MOD_ID, name + "_spawn_egg"));
        SPAWN_EGGS.add(spawnEgg);

        return ENTITY_DEFERRED.register(name, () -> entity);
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        EntitySpawnPlacementRegistry.register(ADELIE_PENGUIN.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AdeliePenguinEntity::canPenguinSpawn);
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ADELIE_PENGUIN.get(), AdeliePenguinEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnEggs(RegistryEvent.Register<Item> event) {
        for (Item spawnEgg : SPAWN_EGGS) {
            Preconditions.checkNotNull(spawnEgg.getRegistryName(), "registryName");
            event.getRegistry().register(spawnEgg);
        }
    }
}