package com.girafi.waddles;

import com.girafi.waddles.entity.AdeliePenguinEntity;
import com.girafi.waddles.init.PenguinRegistry;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.girafi.waddles.init.PenguinRegistry.*;

@Mod(value = Constants.MOD_ID)
public class Waddles {
    public static final DeferredRegister<Item> ITEM_DEFERRED = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    public Waddles() {
        CommonClass.init();
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);

        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec); //Uncomment when building, otherwise have it commented out.

        registerDeferredRegistries(eventBus);
        register();
    }

    public void setupCommon(final FMLCommonSetupEvent event) {
    }

    public void setupClient(final FMLClientSetupEvent event) {
    }

    public void register() {
        PENGUINS.forEach((penguin, name) -> {
            Supplier<Item> spawnEgg = ITEM_DEFERRED.register(name + "_spawn_egg", () -> new ForgeSpawnEggItem(penguin, PENGUIN_EGG_PRIMARY.get(penguin), PENGUIN_EGG_SECONDARY.get(penguin), new Item.Properties()));
            SPAWN_EGGS.add(spawnEgg);
        });
        PenguinSpawn.BIOME_MODIFIER_SERIALIZERS_DEFERRED.register("penguin_spawn", PenguinSpawn.PenguinBiomeModifier::makeCodec);
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        PenguinSpawn.BIOME_MODIFIER_SERIALIZERS_DEFERRED.register(modBus);
        ITEM_DEFERRED.register(modBus);
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Events {
        @SubscribeEvent
        public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
            event.register(PenguinRegistry.ADELIE_PENGUIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AdeliePenguinEntity::canPenguinSpawn, SpawnPlacementRegisterEvent.Operation.AND);

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