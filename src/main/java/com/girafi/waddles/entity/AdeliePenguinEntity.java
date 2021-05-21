package com.girafi.waddles.entity;

import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.init.WaddlesSounds;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.loot.LootTables;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class AdeliePenguinEntity extends AnimalEntity {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.COD, Items.SALMON);
    public short rotationFlipper;
    private boolean moveFlipper = false;

    public AdeliePenguinEntity(EntityType<? extends AdeliePenguinEntity> adelie, World world) {
        super(adelie, world);
        this.maxUpStep = 1.0F;
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
        this.goalSelector.addGoal(9, new LookAtGoal(this, AdeliePenguinEntity.class, 6.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.16D);
    }

    @Override
    public float getWalkTargetValue(@Nonnull BlockPos pos, @Nonnull IWorldReader world) {
        Block blockDown = world.getBlockState(pos.below()).getBlock();
        if (blockDown.getRegistryName() != null && ConfigurationHandler.GENERAL.spawnBlocks.get().contains(blockDown.getRegistryName().toString())) {
            return 10.0F;
        } else {
            return super.getWalkTargetValue(pos, world);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isBaby() ? WaddlesSounds.ADELIE_BABY_AMBIENT.get() : WaddlesSounds.ADELIE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource source) {
        return WaddlesSounds.ADELIE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return WaddlesSounds.ADELIE_DEATH.get();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level.isClientSide) {
            if (this.getX() != this.zo) {
                if (this.moveFlipper) {
                    this.rotationFlipper++;
                }
            }
        }
    }

    @Override
    protected int getExperienceReward(@Nonnull PlayerEntity player) {
        if (ConfigurationHandler.GENERAL.dropExp.get()) {
            return super.getExperienceReward(player);
        }
        return 0;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isFood(@Nonnull ItemStack stack) {
        return !stack.isEmpty() && TEMPTATION_ITEMS.test(stack);
    }


    @Override
    @Nonnull
    public ResourceLocation getDefaultLootTable() {
        return ConfigurationHandler.GENERAL.dropFish.get() ? super.getDefaultLootTable() : LootTables.EMPTY;
    }

    @Override
    public AgeableEntity getBreedOffspring(@Nonnull ServerWorld world, @Nonnull AgeableEntity ageableEntity) {
        return PenguinRegistry.ADELIE_PENGUIN.get().create(this.level);
    }

    public static boolean canPenguinSpawn(EntityType<? extends AdeliePenguinEntity> animal, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && world.getRawBrightness(pos, 0) > 8 && world.canSeeSky(pos);
    }

    @Override
    protected float getStandingEyeHeight(@Nonnull Pose pose, @Nonnull EntitySize size) {
        return this.isBaby() ? 0.5F : 0.9F;
    }

    private class EntityAIExtinguishFire extends PanicGoal {
        EntityAIExtinguishFire() {
            super(AdeliePenguinEntity.this, 2.0D);
        }

        @Override
        public boolean canUse() {
            return (AdeliePenguinEntity.this.isBaby() || AdeliePenguinEntity.this.isOnFire()) && super.canUse();
        }
    }
}