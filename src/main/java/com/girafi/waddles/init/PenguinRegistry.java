package com.girafi.waddles.init;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.entity.AdeliePenguinEntity;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@EventBusSubscriber(modid = Waddles.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class PenguinRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_DEFERRED = DeferredRegister.create(ForgeRegistries.ENTITIES, Waddles.MOD_ID);
    private static final List<Item> SPAWN_EGGS = Lists.newArrayList();

    public static final RegistryObject<EntityType<AdeliePenguinEntity>> ADELIE_PENGUIN = createEntity("adelie_penguin", AdeliePenguinEntity::new, 0.4F, 0.95F, 0x000000, 0xFFFFFF);

    private static <T extends Animal> RegistryObject<EntityType<T>> createEntity(String name, EntityType.EntityFactory<T> factory, float width, float height, int eggPrimary, int eggSecondary) {
        ResourceLocation location = new ResourceLocation(Waddles.MOD_ID, name);
        EntityType<T> entity = EntityType.Builder.of(factory, MobCategory.CREATURE).sized(width, height).setTrackingRange(64).setUpdateInterval(1).build(location.toString());
        Item spawnEgg = new SpawnEggItem(entity, eggPrimary, eggSecondary, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC));
        spawnEgg.setRegistryName(new ResourceLocation(Waddles.MOD_ID, name + "_spawn_egg"));
        SPAWN_EGGS.add(spawnEgg);

        return ENTITY_DEFERRED.register(name, () -> entity);
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        SpawnPlacements.register(ADELIE_PENGUIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AdeliePenguinEntity::canPenguinSpawn);
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