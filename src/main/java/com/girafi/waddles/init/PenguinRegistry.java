package com.girafi.waddles.init;

import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.girafi.waddles.utils.Reference;
import com.google.common.base.CaseFormat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.function.Function;

public class PenguinRegistry {
    public static final EntityType ADELIE_PENGUIN = createEntity(EntityAdeliePenguin.class, EntityAdeliePenguin::new);

    private static EntityType createEntity(Class<? extends Entity> entityClass, Function<? super World, ? extends Entity> entityInstance) {
        Identifier id = classToRegistryName(entityClass);
        return Registry.register(Registry.ENTITY_TYPE, id, new EntityType<>(entityClass, entityInstance, true, true, null));
    }

    private static Identifier classToRegistryName(Class<? extends Entity> entityClass) {
        return new Identifier(Reference.MOD_ID, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityClass.getSimpleName()).replace("entity_", ""));
    }
}