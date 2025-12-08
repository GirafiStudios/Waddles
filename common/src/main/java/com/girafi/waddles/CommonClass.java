package com.girafi.waddles;

import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.init.WaddlesSounds;
import com.girafi.waddles.utils.WaddlesTags;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;

public class CommonClass {

    public static final DispenseItemBehavior DEFAULT_DISPENSE_BEHAVIOR = (source, stack) -> {
        Direction face = source.state().getValue(DispenserBlock.FACING);
        EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack);

        try {
            type.spawn(source.level(), stack, null, source.pos().relative(face), EntitySpawnReason.DISPENSER, face != Direction.UP, false);
        } catch (Exception exception) {
            DispenseItemBehavior.LOGGER.error("Error while dispensing spawn egg from dispenser at {}", source.pos(), exception);
            return ItemStack.EMPTY;
        }

        stack.shrink(1);
        source.level().gameEvent(null, GameEvent.ENTITY_PLACE, source.pos());
        return stack;
    };

    public static void init() {
        WaddlesTags.load();
        WaddlesSounds.load();
        PenguinRegistry.load();
    }
}