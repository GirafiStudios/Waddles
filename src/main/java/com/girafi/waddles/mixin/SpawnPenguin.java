package com.girafi.waddles.mixin;

import com.girafi.waddles.init.PenguinRegistry;
import net.minecraft.entity.EntityCategory;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SnowyTundraBiome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowyTundraBiome.class)
public class SpawnPenguin extends Biome {

    protected SpawnPenguin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("RETURN"), method = "<init>()V")
    private void init(CallbackInfo info) {
        this.addSpawn(EntityCategory.CREATURE, new SpawnEntry(PenguinRegistry.ADELIE_PENGUIN, 8, 1, 4));
    }
}