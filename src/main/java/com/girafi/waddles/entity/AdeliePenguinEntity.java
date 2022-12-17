package com.girafi.waddles.entity;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.init.WaddlesSounds;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import javax.annotation.Nonnull;

public class AdeliePenguinEntity extends Animal {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.COD, Items.SALMON);
    public short rotationFlipper;
    private boolean moveFlipper = false;

    public AdeliePenguinEntity(EntityType<? extends AdeliePenguinEntity> adelie, Level level) {
        super(adelie, level);
        this.maxUpStep = 1.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EntityAIExtinguishFire());
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, PolarBear.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.0D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.16D);
    }

    @Override
    public float getWalkTargetValue(@Nonnull BlockPos pos, @Nonnull LevelReader levelReader) {
        BlockState stateDown = levelReader.getBlockState(pos.below());
        if (stateDown.is(Waddles.Tags.PENGUIN_SPAWNABLE_BLOCKS)) {
            return 10.0F;
        } else {
            return super.getWalkTargetValue(pos, levelReader);
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
    public int getExperienceReward() {
        if (ConfigurationHandler.GENERAL.dropExp.get()) {
            return super.getExperienceReward();
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
        return ConfigurationHandler.GENERAL.dropFish.get() ? super.getDefaultLootTable() : BuiltInLootTables.EMPTY;
    }

    @Override
    public AgeableMob getBreedOffspring(@Nonnull ServerLevel serverLevel, @Nonnull AgeableMob ageableMob) {
        return PenguinRegistry.ADELIE_PENGUIN.get().create(this.level);
    }

    public static boolean canPenguinSpawn(EntityType<? extends AdeliePenguinEntity> animal, ServerLevelAccessor serverLevelAccessor, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return serverLevelAccessor.getBlockState(pos.below()).is(Waddles.Tags.PENGUIN_SPAWNABLE_BLOCKS) && isBrightEnoughToSpawn(serverLevelAccessor, pos);
    }

    @Override
    protected float getStandingEyeHeight(@Nonnull Pose pose, @Nonnull EntityDimensions entityDimensions) {
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