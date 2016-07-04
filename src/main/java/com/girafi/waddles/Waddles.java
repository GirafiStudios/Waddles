package com.girafi.waddles;

import biomesoplenty.api.biome.BOPBiomes;
import biomesoplenty.core.BiomesOPlenty;
import com.girafi.waddles.entity.EntityPenguin;
import com.girafi.waddles.proxy.CommonProxy;
import com.girafi.waddles.utils.ConfigurationHandler;
import com.girafi.waddles.utils.Reference;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, acceptedMinecraftVersions="[1.9.4,1.11)", dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class Waddles {
    @Mod.Instance(Reference.MOD_ID)
    public static Waddles instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_ClASS)
    public static CommonProxy proxy;

    public static final ResourceLocation LOOT_ENTITIES_PENGUIN_FISH = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/penguin"));

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "Waddles.cfg"));
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerModEntity(EntityPenguin.class, "penguin", 0, Waddles.instance, 64, 1, true, 0x000000, 0xFFFFFF);
        addPenguinSpawn(Biomes.ICE_PLAINS, Biomes.MUTATED_ICE_FLATS, Biomes.FROZEN_OCEAN, Biomes.COLD_BEACH);
        if (Loader.isModLoaded(BiomesOPlenty.MOD_ID)) {
            if (BOPBiomes.glacier.isPresent()) {
                addPenguinSpawn(BOPBiomes.glacier.get());
            }
            if (BOPBiomes.gravel_beach.isPresent()) {
                addPenguinSpawn(BOPBiomes.gravel_beach.get());
            }
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    private void addPenguinSpawn(Biome... biomes) {
        EntityRegistry.addSpawn(EntityPenguin.class, 2, 2, 5, EnumCreatureType.CREATURE, biomes);
    }
}