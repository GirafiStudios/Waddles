package com.girafi.waddles.entity;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.init.WaddlesSounds;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTables;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class EntityAdeliePenguin extends AnimalEntity {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.COD, Items.SALMON);
    public short rotationFlipper;
    private boolean moveFlipper = false;

    public EntityAdeliePenguin(EntityType<? extends EntityAdeliePenguin> adelie, World world) {
        super(PenguinRegistry.ADELIE_PENGUIN, world);
        this.stepHeight = 1.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new EntityAIExtinguishFire());
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, PolarBearEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(9, new LookAtGoal(this, EntityAdeliePenguin.class, 6.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.16D);
    }

    @Override
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
    }

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
    protected int getExperiencePoints(PlayerEntity player) {
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
        return LootTables.EMPTY;
    }

    @Override
    @Nonnull
    public EntityAdeliePenguin createChild(@Nonnull AgeableEntity ageable) {
        return Objects.requireNonNull(PenguinRegistry.ADELIE_PENGUIN.create(this.world));
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return this.isChild() ? 0.5F : 0.9F;
    }

    @Override
    public boolean canSpawn(IWorld world, @Nonnull SpawnReason spawnReason) {
        int x = MathHelper.floor(this.posX);
        int y = MathHelper.floor(this.getBoundingBox().minY);
        int z = MathHelper.floor(this.posZ);
        BlockPos pos = new BlockPos(x, y, z);
        Material material = world.getBlockState(pos.down()).getMaterial();
        return (world.getLightSubtracted(pos, 0) > 8 && (material == Material.ICE || material == Material.PACKED_ICE)) || super.canSpawn(world, spawnReason);
    }

    private class EntityAIExtinguishFire extends PanicGoal {
        EntityAIExtinguishFire() {
            super(EntityAdeliePenguin.this, 2.0D);
        }

        @Override
        public boolean shouldExecute() {
            return (EntityAdeliePenguin.this.isChild() || EntityAdeliePenguin.this.isBurning()) && super.shouldExecute();
        }
    }
}