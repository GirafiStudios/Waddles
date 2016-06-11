package com.girafi.waddles.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityPenguin extends EntityAnimal {
    public short rotationFlipper;
    public boolean moveFlipper = false;

    public EntityPenguin(World world) {
        super(world);
        this.setSize(0.4F, 0.7F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIExtinguishFire());
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(3, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(4, new EntityAITempt(this, 1.1D, Items.FISH, false));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPenguin.class, 6.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (worldObj.isRemote) {
            if (this.posZ != this.prevPosZ) {
                if (moveFlipper) {
                    rotationFlipper++;
                }
            }
        }
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isBreedingItem(@Nullable ItemStack stack) {
        return stack == null ? false : stack.getItem() == Items.FISH;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return new EntityPenguin(this.worldObj);
    }

    private class EntityAIExtinguishFire extends EntityAIPanic {
        public EntityAIExtinguishFire() {
            super(EntityPenguin.this, 2.0);
        }

        @Override
        public boolean shouldExecute() {
            return (EntityPenguin.this.isChild() || EntityPenguin.this.isBurning()) && super.shouldExecute();
        }
    }
}