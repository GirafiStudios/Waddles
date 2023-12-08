package com.girafi.waddles.init;

import com.girafi.waddles.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.HashMap;
import java.util.function.Supplier;

public class WaddlesSounds {
    public static final HashMap<String, Supplier<SoundEvent>> SOUND_MAP = new HashMap<>();
    public static final Supplier<SoundEvent> ADELIE_AMBIENT = createSound("adelie.ambient");
    public static final Supplier<SoundEvent> ADELIE_BABY_AMBIENT = createSound("adelie.baby.ambient");
    public static final Supplier<SoundEvent> ADELIE_DEATH = createSound("adelie.death");
    public static final Supplier<SoundEvent> ADELIE_HURT = createSound("adelie.hurt");

    private static Supplier<SoundEvent> createSound(String name) {
        ResourceLocation resourceLocation = new ResourceLocation(Constants.MOD_ID, name);
        Supplier<SoundEvent> soundEvent = () -> SoundEvent.createVariableRangeEvent(resourceLocation);
        SOUND_MAP.put(name, soundEvent);
        return soundEvent;
    }
}