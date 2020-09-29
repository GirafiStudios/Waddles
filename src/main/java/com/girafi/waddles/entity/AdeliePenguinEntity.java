package com.girafi.waddles.entity;

import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.init.WaddlesSounds;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
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
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class AdeliePenguinEntity extends AnimalEntity {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.COD, Items.SALMON);
    public short rotationFlipper;
    private boolean moveFlipper = false;

    public AdeliePenguinEntity(EntityType<? extends AdeliePenguinEntity> adelie, World world) {
        super(adelie, world);
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
        this.goalSelector.addGoal(9, new LookAtGoal(this, AdeliePenguinEntity.class, 6.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute getAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.16D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isChild() ? WaddlesSounds.ADELIE_BABY_AMBIENT : WaddlesSounds.ADELIE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource source) {
        return WaddlesSounds.ADELIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return WaddlesSounds.ADELIE_DEATH;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.world.isRemote) {
            if (this.getPosX() != this.prevPosZ) {
                if (this.moveFlipper) {
                    this.rotationFlipper++;
                }
            }
        }
    }

    @Override
    protected int getExperiencePoints(@Nonnull PlayerEntity player) {
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

    @Override
    @Nonnull
    public ResourceLocation getLootTable() {
        return ConfigurationHandler.GENERAL.dropFish.get() ? super.getLootTable() : LootTables.EMPTY;
    }

    @Override
    public AgeableEntity func_241840_a(@Nonnull ServerWorld world, @Nonnull AgeableEntity ageableEntity) {
        return PenguinRegistry.ADELIE_PENGUIN.create(this.world);
    }

    public static boolean canPenguinSpawn(EntityType<? extends AdeliePenguinEntity> animal, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(Blocks.GRASS_BLOCK) && world.getLightSubtracted(pos, 0) > 8 && world.canSeeSky(pos);
    }

    @Override
    protected float getStandingEyeHeight(@Nonnull Pose pose, @Nonnull EntitySize size) {
        return this.isChild() ? 0.5F : 0.9F;
    }

    private class EntityAIExtinguishFire extends PanicGoal {
        EntityAIExtinguishFire() {
            super(AdeliePenguinEntity.this, 2.0D);
        }

        @Override
        public boolean shouldExecute() {
            return (AdeliePenguinEntity.this.isChild() || AdeliePenguinEntity.this.isBurning()) && super.shouldExecute();
        }
    }
}