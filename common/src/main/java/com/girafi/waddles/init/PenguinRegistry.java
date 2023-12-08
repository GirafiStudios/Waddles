package com.girafi.waddles.init;

import com.girafi.waddles.Constants;
import com.girafi.waddles.entity.AdeliePenguinEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Supplier;

public class PenguinRegistry {
    public static final Collection<Supplier<Item>> SPAWN_EGGS = new ArrayList<>();
    public static final HashMap<String, Supplier<EntityType<? extends AdeliePenguinEntity>>> PENGUINS = new HashMap<>();
    public static final HashMap<Supplier<EntityType<? extends AdeliePenguinEntity>>, Float> PENGUIN_WIDTHS = new HashMap<>();
    public static final HashMap<Supplier<EntityType<? extends AdeliePenguinEntity>>, Float> PENGUIN_HEIGHTS = new HashMap<>();
    public static final HashMap<Supplier<EntityType<? extends AdeliePenguinEntity>>, Integer> PENGUIN_EGG_PRIMARY = new HashMap<>();
    public static final HashMap<Supplier<EntityType<? extends AdeliePenguinEntity>>, Integer> PENGUIN_EGG_SECONDARY = new HashMap<>();

    public static final Supplier<EntityType<? extends AdeliePenguinEntity>> ADELIE_PENGUIN = registerPenguin("adelie_penguin", () -> AdeliePenguinEntity::new, 0.4F, 0.95F, 0x000000, 0xFFFFFF);

    private static <T extends AdeliePenguinEntity> Supplier<EntityType<? extends AdeliePenguinEntity>> registerPenguin(String name, Supplier<EntityType.EntityFactory<T>> factory, float width, float height, int eggPrimary, int eggSecondary) {
        ResourceLocation location = new ResourceLocation(Constants.MOD_ID, name);
        Supplier<EntityType<? extends AdeliePenguinEntity>> entityType = () -> EntityType.Builder.of(factory.get(), MobCategory.CREATURE).sized(width, height).clientTrackingRange(64).updateInterval(1).build(location.toString());

        PENGUINS.put(name, entityType);
        PENGUIN_WIDTHS.put(entityType, width);
        PENGUIN_HEIGHTS.put(entityType, height);
        PENGUIN_EGG_PRIMARY.put(entityType, eggPrimary);
        PENGUIN_EGG_SECONDARY.put(entityType, eggSecondary);
        return entityType;
    }
}