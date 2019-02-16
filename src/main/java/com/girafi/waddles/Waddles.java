package com.girafi.waddles;

import com.girafi.waddles.client.renderer.RenderPenguin;
import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.girafi.waddles.init.PenguinRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;

public class Waddles implements ModInitializer, ClientModInitializer {
    //public static final Identifier LOOT_ENTITIES_PENGUIN_FISH = LootTables.registerLootTable(new Identifier(Reference.MOD_ID, "entities/adelie_penguin"));

    @Override
    public void onInitialize() {
        //ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "Waddles.cfg"));
        new PenguinRegistry();
    }

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(EntityAdeliePenguin.class, (dispatcher, context) -> new RenderPenguin(dispatcher));
    }
}