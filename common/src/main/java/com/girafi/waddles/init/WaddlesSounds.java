package com.girafi.waddles.init;

import com.girafi.waddles.Constants;
import com.girafi.waddles.registration.RegistrationProvider;
import com.girafi.waddles.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class WaddlesSounds {
    public static final RegistrationProvider<SoundEvent> SOUNDS = RegistrationProvider.get(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);
    public static final RegistryObject<SoundEvent, SoundEvent> ADELIE_AMBIENT = createSound("adelie.ambient");
    public static final RegistryObject<SoundEvent, SoundEvent> ADELIE_BABY_AMBIENT = createSound("adelie.baby.ambient");
    public static final RegistryObject<SoundEvent, SoundEvent> ADELIE_DEATH = createSound("adelie.death");
    public static final RegistryObject<SoundEvent, SoundEvent> ADELIE_HURT = createSound("adelie.hurt");

    private static RegistryObject<SoundEvent, SoundEvent> createSound(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(Constants.MOD_ID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    //Needed to statically initialize fields
    public static void load() {}
}