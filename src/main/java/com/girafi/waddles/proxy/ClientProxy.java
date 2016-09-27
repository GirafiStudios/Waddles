package com.girafi.waddles.proxy;

import com.girafi.waddles.client.renderer.RenderPenguin;
import com.girafi.waddles.entity.EntityAdeliePenguin;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityAdeliePenguin.class, new IRenderFactory<EntityAdeliePenguin>() {
            @Override
            public Render<? super EntityAdeliePenguin> createRenderFor(RenderManager manager) {
                return new RenderPenguin(manager);
            }
        });
    }
}