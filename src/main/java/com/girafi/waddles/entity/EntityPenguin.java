package com.girafi.waddles.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class EntityPenguin extends EntityAnimal {

    public EntityPenguin(World world) {
        super(world);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }
}
