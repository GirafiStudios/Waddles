package com.girafi.waddles.client.renderer;

import com.girafi.waddles.client.model.ModelPenguin;
import com.girafi.waddles.entity.EntityAdeliePenguin;
import com.girafi.waddles.utils.Reference;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.util.Identifier;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class RenderPenguin extends LivingEntityRenderer<EntityAdeliePenguin, ModelPenguin> {

    public RenderPenguin(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new ModelPenguin(), 0.5F);
    }

    @Override
    protected Identifier getTexture(EntityAdeliePenguin penguin) {
        if (penguin.hasCustomName()) {
            String name = Objects.requireNonNull(penguin.getCustomName()).getString().toLowerCase().trim();
            if (name.equals("joshie") || name.equals("joshiejack")) {
                return this.getPenguinTexture("joshie");
            } else if (name.equals("darkosto")) {
                return this.getPenguinTexture("darkosto");
            }
        }
        return penguin.isChild() ? this.getPenguinTexture("adelie_child") : this.getPenguinTexture("adelie");
    }

    private Identifier getPenguinTexture(String fileName) {
        return new Identifier(Reference.MOD_ID, "textures/entity/penguin/" + fileName + ".png");
    }
}