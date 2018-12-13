package com.girafi.waddles.client.renderer;

import net.fabricmc.fabric.client.render.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;

public class RenderFactoryPenguin implements EntityRendererRegistry.Factory {

    @Override
    public EntityRenderer<? extends Entity> create(EntityRenderDispatcher renderDispatcher, EntityRendererRegistry.Context context) {
        return new RenderPenguin(renderDispatcher);
    }
}