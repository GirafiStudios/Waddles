package com.girafi.waddles;

import com.girafi.waddles.entity.AdeliePenguinEntity;
import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.girafi.waddles.init.PenguinRegistry.*;

@Mod(Constants.MOD_ID)
public class Waddles {
    public static final DeferredRegister.Items ITEM_DEFERRED = DeferredRegister.createItems(Constants.MOD_ID);

    public Waddles(IEventBus eventBus) {
        CommonClass.init();
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec);

        registerDeferredRegistries(eventBus);
        register();
    }

    public void setupCommon(final FMLCommonSetupEvent event) {
    }

    public void setupClient(final FMLClientSetupEvent event) {
    }

    public void register() {
        PENGUINS.forEach((penguin, name) -> {
            Supplier<Item> spawnEgg = ITEM_DEFERRED.register(name + "_spawn_egg", () -> new DeferredSpawnEggItem(penguin, PENGUIN_EGG_PRIMARY.get(penguin), PENGUIN_EGG_SECONDARY.get(penguin), new Item.Properties()));
            SPAWN_EGGS.add(spawnEgg);
        });
        PenguinSpawn.BIOME_MODIFIER_SERIALIZERS_DEFERRED.register("penguin_spawn", PenguinSpawn.PenguinBiomeModifier::makeCodec);
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        PenguinSpawn.BIOME_MODIFIER_SERIALIZERS_DEFERRED.register(modBus);
        ITEM_DEFERRED.register(modBus);
    }

    @EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class Events {
        @SubscribeEvent
        public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
            event.register(PenguinRegistry.ADELIE_PENGUIN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AdeliePenguinEntity::canPenguinSpawn, SpawnPlacementRegisterEvent.Operation.AND);
        }

        @SubscribeEvent
        public static void addEntityAttributes(EntityAttributeCreationEvent event) {
            event.put(PenguinRegistry.ADELIE_PENGUIN.get(), AdeliePenguinEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
                PenguinRegistry.SPAWN_EGGS.forEach(stack -> event.accept(stack.get()));
            }
        }
    }
}