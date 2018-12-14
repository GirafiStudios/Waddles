package com.girafi.waddles.init;

import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.girafi.waddles.utils.Reference;
import com.google.common.base.CaseFormat;
import net.fabricmc.fabric.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.function.Function;

public class PenguinRegistry {
    public static final EntityType ADELIE_PENGUIN = createEntity(EntityAdeliePenguin.class, EntityAdeliePenguin::new, 0, 16777215);

    private static EntityType createEntity(Class<? extends Entity> entityClass, Function<? super World, ? extends Entity> entityInstance, int eggPrimary, int eggSecondary) {
        String name = classToRegistryName(entityClass);
        Identifier id = new Identifier(Reference.MOD_ID, name);
        EntityType entityType = Registry.register(Registry.ENTITY_TYPE, id, FabricEntityTypeBuilder.create(entityClass, entityInstance).trackable(64, 1).build(id.toString()));
        Item spawnEgg = new SpawnEggItem(entityType, eggPrimary, eggSecondary, (new Item.Settings()).itemGroup(ItemGroup.MISC));
        Registry.register(Registry.ITEM, new Identifier(Reference.MOD_ID, name + "_spawn_egg"), spawnEgg);
        return entityType;
    }

    private static String classToRegistryName(Class<? extends Entity> entityClass) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityClass.getSimpleName()).replace("entity_", "");
    }
}