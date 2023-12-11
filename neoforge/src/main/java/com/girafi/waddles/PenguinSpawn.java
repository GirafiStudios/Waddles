package com.girafi.waddles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class PenguinSpawn {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS_DEFERRED = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Constants.MOD_ID);

    public record PenguinBiomeModifier(HolderSet<Biome> includeList, HolderSet<Biome> excludeList, MobSpawnSettings.SpawnerData spawn) implements BiomeModifier {

        @Override
        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD && this.includeList.contains(biome) && !this.excludeList.contains(biome)) {
                builder.getMobSpawnSettings().addSpawn(this.spawn.type.getCategory(), this.spawn);
            }
        }

        @Override
        public Codec<? extends BiomeModifier> codec() {
            return makeCodec();
        }

        public static Codec<PenguinBiomeModifier> makeCodec() {
            return RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("includeBiomes").forGetter(PenguinBiomeModifier::includeList),
                    Biome.LIST_CODEC.fieldOf("excludeBiomes").forGetter(PenguinBiomeModifier::excludeList),
                    MobSpawnSettings.SpawnerData.CODEC.fieldOf("spawn").forGetter(PenguinBiomeModifier::spawn)
            ).apply(builder, PenguinBiomeModifier::new));
        }
    }
}