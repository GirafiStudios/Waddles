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
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.fml.config.ModConfig;

import java.util.function.Predicate;

import static com.girafi.waddles.init.PenguinRegistry.*;

public class Waddles implements ModInitializer {
    private static final DispenseItemBehavior DEFAULT_DISPENSE_BEHAVIOR = (source, stack) -> {
        Direction face = source.state().getValue(DispenserBlock.FACING);
        EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack);

        try {
            type.spawn(source.level(), stack, null, source.pos().relative(face), MobSpawnType.DISPENSER, face != Direction.UP, false);
        } catch (Exception exception) {
            DispenseItemBehavior.LOGGER.error("Error while dispensing spawn egg from dispenser at {}", source.pos(), exception);
            return ItemStack.EMPTY;
        }

        stack.shrink(1);
        source.level().gameEvent(GameEvent.ENTITY_PLACE, source.pos(), GameEvent.Context.of(source.state()));
        return stack;
    };

    @Override
    public void onInitialize() {
        CommonClass.init();
        NeoForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, ConfigurationHandler.spec);

        register();
        BiomeModifications.addSpawn(penguinSpawnSelector(), MobCategory.CREATURE, ADELIE_PENGUIN.get(), 100, 1, 5);
    }

    public void register() {
        PENGUINS.forEach((penguin, name) -> {
            Item spawnEgg = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name + "_spawn_egg"), new SpawnEggItem(penguin.get(), PENGUIN_EGG_PRIMARY.get(penguin), PENGUIN_EGG_SECONDARY.get(penguin), new Item.Properties()));
            FabricDefaultAttributeRegistry.register(penguin.get(), AdeliePenguinEntity.createAttributes());
            DispenserBlock.registerBehavior(spawnEgg, DEFAULT_DISPENSE_BEHAVIOR);
            ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(content -> content.accept(spawnEgg));
            SpawnPlacements.register(penguin.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AdeliePenguinEntity::canPenguinSpawn);
        });
    }

    public static Predicate<BiomeSelectionContext> penguinSpawnSelector() {
        return (context) -> context.hasTag(WaddlesTags.SPAWN_INCLUDE_LIST) && !context.hasTag(WaddlesTags.SPAWN_EXCLUDE_LIST);
    }
}