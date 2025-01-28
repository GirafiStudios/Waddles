package com.girafi.waddles;

import com.girafi.waddles.entity.AdeliePenguinEntity;
import com.girafi.waddles.utils.ConfigurationHandler;
import com.girafi.waddles.utils.WaddlesTags;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.fml.config.ModConfig;

import java.util.function.Predicate;

import static com.girafi.waddles.init.PenguinRegistry.ADELIE_PENGUIN;
import static com.girafi.waddles.init.PenguinRegistry.PENGUINS;

public class Waddles implements ModInitializer {

    @Override
    public void onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, ConfigurationHandler.spec);
        CommonClass.init();

        register();
        BiomeModifications.addSpawn(penguinSpawnSelector(), MobCategory.CREATURE, ADELIE_PENGUIN.get(), 100, 1, 5);
    }

    public void register() {
        PENGUINS.forEach((penguin, name) -> {
            Item spawnEgg = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name + "_spawn_egg"), new SpawnEggItem(penguin.get(), new Item.Properties()));
            FabricDefaultAttributeRegistry.register(penguin.get(), AdeliePenguinEntity.createAttributes());
            DispenserBlock.registerBehavior(spawnEgg, CommonClass.DEFAULT_DISPENSE_BEHAVIOR);
            ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(content -> content.accept(spawnEgg));
            SpawnPlacements.register(penguin.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AdeliePenguinEntity::canPenguinSpawn);
        });
    }

    public static Predicate<BiomeSelectionContext> penguinSpawnSelector() {
        return (context) -> context.hasTag(WaddlesTags.SPAWN_INCLUDE_LIST) && !context.hasTag(WaddlesTags.SPAWN_EXCLUDE_LIST);
    }
}