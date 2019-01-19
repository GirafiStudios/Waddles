package com.girafi.waddles.entity;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityAdeliePenguin extends EntityAnimal {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.COD, Items.SALMON);
    public short rotationFlipper;
    private boolean moveFlipper = false;

    public EntityAdeliePenguin(World world) {
        super(PenguinRegistry.ADELIE_PENGUIN, world);
        this.setSize(0.4F, 0.95F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIExtinguishFire());
        this.tasks.addTask(2, new EntityAIPanic(this, 1.5D));
        this.tasks.addTask(3, new EntityAIMate(this, 0.8D));
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityPolarBear.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(5, new EntityAITempt(this, 1.0D, TEMPTATION_ITEMS, false));
        this.tasks.addTask(6, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityAdeliePenguin.class, 6.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.16D);
    }

    /*@Override
    protected SoundEvent getAmbientSound() {
        return this.isChild() ? WaddlesSounds.ADELIE_BABY_AMBIENT : WaddlesSounds.ADELIE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return WaddlesSounds.ADELIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return WaddlesSounds.ADELIE_DEATH;
    }*/

    @Override
    public void livingTick() {
        super.livingTick();
        if (world.isRemote) {
            if (this.posZ != this.prevPosZ) {
                if (moveFlipper) {
                    rotationFlipper++;
                }
            }
        }
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        if (ConfigurationHandler.GENERAL.dropExp.get()) {
            return super.getExperiencePoints(player);
        }
        return 0;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isBreedingItem(@Nonnull ItemStack stack) {
        return !stack.isEmpty() && TEMPTATION_ITEMS.test(stack);
    }

    @Nullable
    @Override
    public ResourceLocation getLootTable() {
        if (ConfigurationHandler.GENERAL.dropFish.get()) {
            return Waddles.LOOT_ENTITIES_PENGUIN_FISH;
        }
        return LootTableList.EMPTY;
    }

    @Override
    @Nonnull
    public EntityAdeliePenguin createChild(@Nonnull EntityAgeable ageable) {
        return new EntityAdeliePenguin(this.world);
    }

    @Override
    public float getEyeHeight() {
        return this.isChild() ? 0.5F : 0.9F;
    }

    private class EntityAIExtinguishFire extends EntityAIPanic {
        EntityAIExtinguishFire() {
            super(EntityAdeliePenguin.this, 2.0D);
        }

        @Override
        public boolean shouldExecute() {
            return (EntityAdeliePenguin.this.isChild() || EntityAdeliePenguin.this.isBurning()) && super.shouldExecute();
        }
    }
}