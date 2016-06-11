package com.girafi.waddles;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import com.girafi.waddles.entity.EntityPenguin;
import com.girafi.waddles.proxy.CommonProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.DEPENDENCIES)
public class Waddles {
    @Mod.Instance(Reference.MOD_ID)
    public static Waddles instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_ClASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerModEntity(EntityPenguin.class, "penguin", 0, Waddles.instance, 64, 1, true, 0x000000, 0xFFFFFF);
        EntityRegistry.addSpawn(EntityPenguin.class, 1, 1, 4, EnumCreatureType.CREATURE, Biomes.FROZEN_OCEAN, Biomes.ICE_PLAINS, Biomes.COLD_BEACH);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}