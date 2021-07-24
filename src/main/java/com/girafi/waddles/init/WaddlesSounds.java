package com.girafi.waddles.init;

import com.girafi.waddles.Waddles;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WaddlesSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENT_DEFERRED = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Waddles.MOD_ID);
    public static final RegistryObject<SoundEvent> ADELIE_AMBIENT = createSound("adelie.ambient");
    public static final RegistryObject<SoundEvent> ADELIE_BABY_AMBIENT = createSound("adelie.baby.ambient");
    public static final RegistryObject<SoundEvent> ADELIE_DEATH = createSound("adelie.death");
    public static final RegistryObject<SoundEvent> ADELIE_HURT = createSound("adelie.hurt");

    private static RegistryObject<SoundEvent> createSound(String name) {
        ResourceLocation resourceLocation = new ResourceLocation(Waddles.MOD_ID, name);
        return SOUND_EVENT_DEFERRED.register(name, () -> new SoundEvent(resourceLocation));
    }
}