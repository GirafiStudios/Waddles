package com.girafi.waddles;

import com.girafi.waddles.entity.AdeliePenguinEntity;
import com.girafi.waddles.utils.ConfigurationHandler;
import fuzs.forgeconfigapiport.api.config.v3.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.fml.config.ModConfig;

import java.util.function.Supplier;

import static com.girafi.waddles.init.PenguinRegistry.*;

public class Waddles implements ModInitializer {
    private static final DispenseItemBehavior DEFAULT_DISPENSE_BEHAVIOR = (source, stack) -> {
        Direction face = source.state().getValue(DispenserBlock.FACING);
        EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());

        try {
            type.spawn(source.level(), stack, null, source.pos().relative(face), MobSpawnType.DISPENSER, face != Direction.UP, false);
        } catch (Exception exception) {
            DispenseItemBehavior.LOGGER.error("Error while dispensing spawn egg from dispenser at {}", source.pos(), exception);
            return ItemStack.EMPTY;
        }

        stack.shrink(1);
        source.level().gameEvent(GameEvent.ENTITY_PLACE, source.pos(), GameEvent.Context.of(source.state()));
        return stack;
    }; //TODO Spawn Placement

    @Override
    public void onInitialize() {
        CommonClass.init();
        ForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, ConfigurationHandler.spec);

        register();
    }

    public void register() {
        PENGUINS.forEach((penguin, name) -> {
            Item spawnEgg = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID,name + "_spawn_egg"), new SpawnEggItem(penguin.get(), PENGUIN_EGG_PRIMARY.get(penguin), PENGUIN_EGG_SECONDARY.get(penguin), new Item.Properties()));
            FabricDefaultAttributeRegistry.register(penguin.get(), AdeliePenguinEntity.createAttributes());
            DispenserBlock.registerBehavior(spawnEgg, DEFAULT_DISPENSE_BEHAVIOR);
            ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(content -> content.accept(spawnEgg));
        });

        //PenguinSpawn.BIOME_MODIFIER_SERIALIZERS_DEFERRED.register("penguin_spawn", PenguinSpawn.PenguinBiomeModifier::makeCodec); //TODO Figure out how to do mob spawns in Fabric
    }
}