package com.girafi.waddles;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.MobSpawnSettingsBuilder;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class PenguinSpawn {
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS_DEFERRED = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Constants.MOD_ID);

    public record PenguinBiomeModifier(HolderSet<Biome> includeList, HolderSet<Biome> excludeList, WeightedList<MobSpawnSettings.SpawnerData> spawners) implements BiomeModifier {

        @Override
        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD && this.includeList.contains(biome) && !this.excludeList.contains(biome)) {
                MobSpawnSettingsBuilder spawns = builder.getMobSpawnSettings();
                for (Weighted<MobSpawnSettings.SpawnerData> spawner : this.spawners.unwrap()) {
                    EntityType<?> type = spawner.value().type();
                    spawns.addSpawn(type.getCategory(), spawner.weight(), spawner.value());
                }
            }
        }

        @Override
        @Nonnull
        public MapCodec<? extends BiomeModifier> codec() {
            return makeCodec();
        }

        public static MapCodec<PenguinBiomeModifier> makeCodec() {
            return RecordCodecBuilder.mapCodec(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("includeBiomes").forGetter(PenguinBiomeModifier::includeList),
                    Biome.LIST_CODEC.fieldOf("excludeBiomes").forGetter(PenguinBiomeModifier::excludeList),
                    Codec.either(WeightedList.codec(MobSpawnSettings.SpawnerData.CODEC), Weighted.codec(MobSpawnSettings.SpawnerData.CODEC)).xmap(
                            either -> either.map(Function.identity(), WeightedList::<MobSpawnSettings.SpawnerData>of), // convert list/singleton to list when decoding
                            list -> list.unwrap().size() == 1 ? Either.right(list.unwrap().get(0)) : Either.left(list) // convert list to singleton/list when encoding
                    ).fieldOf("spawn").forGetter(PenguinBiomeModifier::spawners)
            ).apply(builder, PenguinBiomeModifier::new));
        }
    }
}