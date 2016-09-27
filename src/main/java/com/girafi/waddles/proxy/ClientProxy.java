package com.girafi.waddles.proxy;

import com.girafi.waddles.client.renderer.RenderPenguin;
import com.girafi.waddles.entity.EntityAdeliePenguin;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityAdeliePenguin.class, RenderPenguin::new);
    }
}