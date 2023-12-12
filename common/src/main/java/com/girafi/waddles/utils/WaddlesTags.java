package com.girafi.waddles.utils;

import com.girafi.waddles.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class WaddlesTags {
    public static final TagKey<Block> PENGUIN_SPAWNABLE_BLOCKS = blockTag(Constants.MOD_ID, "penguin_spawnable_blocks");
    public static final TagKey<Biome> SPAWN_INCLUDE_LIST = biomeTag("spawn_include");
    public static final TagKey<Biome> SPAWN_EXCLUDE_LIST = biomeTag("spawn_exclude");

    public static TagKey<Block> blockTag(String modID, String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(modID, name));
    }

    public static TagKey<Biome> biomeTag(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(Constants.MOD_ID, name));
    }

    public static void load() {}
}