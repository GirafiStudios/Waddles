package com.girafi.waddles.init;

import com.girafi.waddles.utils.Reference;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WaddlesSounds {
    public static final SoundEvent ADELIE_AMBIENT = createSound("adelie.ambient");
    public static final SoundEvent ADELIE_BABY_AMBIENT = createSound("adelie.baby.ambient");
    public static final SoundEvent ADELIE_DEATH = createSound("adelie.death");
    public static final SoundEvent ADELIE_HURT = createSound("adelie.hurt");

    private static SoundEvent createSound(String name) {
        Identifier identifier = new Identifier(Reference.MOD_ID, name);
        SoundEvent sound = new SoundEvent(identifier);
        registerSound(sound, identifier);
        return sound;
    }

    public static void registerSound(SoundEvent soundEvent, Identifier identifier) {
        Registry.register(Registry.SOUND_EVENT, identifier, soundEvent);
    }
}