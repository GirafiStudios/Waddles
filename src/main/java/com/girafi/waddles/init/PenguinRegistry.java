package com.girafi.waddles.init;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.entity.AdeliePenguinEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@EventBusSubscriber(modid = Waddles.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class PenguinRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_DEFERRED = DeferredRegister.create(ForgeRegistries.ENTITIES, Waddles.MOD_ID);
    public static final DeferredRegister<Item> ITEM_DEFERRED = DeferredRegister.create(ForgeRegistries.ITEMS, Waddles.MOD_ID);

    public static final RegistryObject<EntityType<AdeliePenguinEntity>> ADELIE_PENGUIN = registerPenguin("adelie_penguin", () -> AdeliePenguinEntity::new, 0.4F, 0.95F, 0x000000, 0xFFFFFF);

    private static <T extends Animal> RegistryObject<EntityType<T>> registerPenguin(String name, Supplier<EntityType.EntityFactory<T>> factory, float width, float height, int eggPrimary, int eggSecondary) {
        ResourceLocation location = new ResourceLocation(Waddles.MOD_ID, name);
        RegistryObject<EntityType<T>> entityType = ENTITY_DEFERRED.register(name, () -> EntityType.Builder.of(factory.get(), MobCategory.CREATURE).sized(width, height).setTrackingRange(64).setUpdateInterval(1).build(location.toString()));

        ITEM_DEFERRED.register(name + "_spawn_egg", () -> new ForgeSpawnEggItem(entityType, eggPrimary, eggSecondary, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
        return entityType;
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ADELIE_PENGUIN.get(), AdeliePenguinEntity.createAttributes().build());
    }
}