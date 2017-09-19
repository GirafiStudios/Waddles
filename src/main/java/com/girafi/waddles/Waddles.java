package com.girafi.waddles;

import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.girafi.waddles.entity.PenguinRegistry;
import com.girafi.waddles.proxy.CommonProxy;
import com.girafi.waddles.utils.ConfigurationHandler;
import com.girafi.waddles.utils.Reference;
import com.google.common.collect.Lists;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.List;
import java.util.Set;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class Waddles {
    @Mod.Instance(Reference.MOD_ID)
    public static Waddles instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_ClASS)
    public static CommonProxy proxy;

    public static final ResourceLocation LOOT_ENTITIES_PENGUIN_FISH = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/penguin"));

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "Waddles.cfg"));
        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        List<Biome> spawnable_biomes = Lists.newArrayList();
        for (Biome biome : Biome.REGISTRY) {
            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);
            if (types.contains(SNOWY) && !types.contains(FOREST) && !types.contains(NETHER) && !biome.getSpawnableList(EnumCreatureType.CREATURE).isEmpty()) {
                spawnable_biomes.add(biome);
            }
        }
        PenguinRegistry.addPenguinSpawn(EntityAdeliePenguin.class, PenguinRegistry.ADELIE_PENGUIN, 2, 1, 4, spawnable_biomes.toArray(new Biome[spawnable_biomes.size()]));
    }
}