package com.girafi.waddles.utils;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WaddlesSounds {
    public static final SoundEvent ADELIE_AMBIENT = register("adelie.ambient");
    public static final SoundEvent ADELIE_BABY_AMBIENT = register("adelie.baby.ambient");
    public static final SoundEvent ADELIE_DEATH = register("adelie.death");
    public static final SoundEvent ADELIE_HURT = register("adelie.hurt");

    private static SoundEvent register(String name) {
        ResourceLocation resourceLocation = new ResourceLocation(Reference.MOD_ID, name);
        return GameRegistry.register(new SoundEvent(resourceLocation), resourceLocation);
    }
}