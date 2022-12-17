package com.girafi.waddles;

import com.girafi.waddles.client.ClientHandler;
import com.girafi.waddles.entity.AdeliePenguinEntity;
import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.init.PenguinSpawn;
import com.girafi.waddles.init.WaddlesSounds;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = Waddles.MOD_ID)
public class Waddles {
    public static final String MOD_ID = "waddles";
    public static final Logger LOG = LogManager.getLogger(StringUtils.capitalize(MOD_ID));

    public Waddles() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);
        PenguinSpawn.BIOME_MODIFIER_SERIALIZERS_DEFERRED.register("penguin_spawn", PenguinSpawn.PenguinBiomeModifier::makeCodec);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec);
        registerDeferredRegistries(eventBus);
    }

    public void setupCommon(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> SpawnPlacements.register(PenguinRegistry.ADELIE_PENGUIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AdeliePenguinEntity::canPenguinSpawn));
    }

    public void setupClient(final FMLClientSetupEvent event) {
        ClientHandler.init();
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        PenguinRegistry.ENTITY_DEFERRED.register(modBus);
        PenguinRegistry.ITEM_DEFERRED.register(modBus);
        WaddlesSounds.SOUND_EVENT_DEFERRED.register(modBus);
        PenguinSpawn.BIOME_MODIFIER_SERIALIZERS_DEFERRED.register(modBus);
    }

    public static class Tags {
        public static final TagKey<Block> PENGUIN_SPAWNABLE_BLOCKS = blockTag(Waddles.MOD_ID, "penguin_spawnable_blocks");
        public static final TagKey<Biome> SPAWN_INCLUDE_LIST = biomeTag("spawn_include");
        public static final TagKey<Biome> SPAWN_EXCLUDE_LIST = biomeTag("spawn_exclude");

        public static TagKey<Block> blockTag(String modID, String name) {
            return BlockTags.create(new ResourceLocation(modID, name));
        }

        public static TagKey<Biome> biomeTag(String name) {
            return TagKey.create(Registries.BIOME, new ResourceLocation(Waddles.MOD_ID, name));
        }

        public static void init() {
        }
    }
}