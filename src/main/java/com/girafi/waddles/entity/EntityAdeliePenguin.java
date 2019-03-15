package com.girafi.waddles.entity;

import com.girafi.waddles.Waddles;
import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.init.WaddlesSounds;
import com.girafi.waddles.utils.ConfigurationHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
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
        this.stepHeight = 1.0F;
        this.moveHelper = new MoveHelper(this);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAIExtinguishFire());
        this.tasks.addTask(1, new EntityAIPanic(this, 1.5D));
        this.tasks.addTask(2, new EntityAIMate(this, 0.8D));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityPolarBear.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(4, new AITempt(this, 1.0D, TEMPTATION_ITEMS));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(6, new AIWander(this, 1.0D, 100));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityAdeliePenguin.class, 6.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.tasks.addTask(10, new AIGetOutOfWater(this, 1.0D));
        this.tasks.addTask(11, new AIWanderSwim(this, 1.0D, 2));
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

        this.updateSize();

        if (world.isRemote) {
            if (this.posZ != this.prevPosZ) {
                if (moveFlipper) {
                    rotationFlipper++;
                }
            }
        }
    }

    private void updateSize() {
        if (this.isInWater()) {
            this.setSize(0.6F, 0.4F);
        } else {
            this.setSize(0.4F, 0.95F);
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
    @Nonnull
    protected PathNavigate createNavigator(@Nonnull World world) {
        return new PathNavigater(this, world);
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
        return this.isInWater() ? this.isChild() ? 0.15F : 0.25F : this.isChild() ? 0.5F : 0.9F;
    }

    @Override
    public boolean canSpawn(IWorld world, boolean fromSpawner) {
        int x = MathHelper.floor(this.posX);
        int y = MathHelper.floor(this.getBoundingBox().minY);
        int z = MathHelper.floor(this.posZ);
        BlockPos pos = new BlockPos(x, y, z);
        Material material = world.getBlockState(pos.down()).getMaterial();
        return (world.getLightSubtracted(pos, 0) > 8 && (material == Material.ICE || material == Material.PACKED_ICE)) || super.canSpawn(world, fromSpawner);
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

    static class MoveHelper extends EntityMoveHelper {
        private final EntityAdeliePenguin penguin;

        MoveHelper(EntityAdeliePenguin penguin) {
            super(penguin);
            this.penguin = penguin;
        }

        private void updateSpeed() {
            if (this.penguin.isInWater()) {
                this.penguin.motionY += 0.005D;
                if (this.penguin.isChild()) {
                    this.penguin.setAIMoveSpeed(Math.max(this.penguin.getAIMoveSpeed() / 2.5F, 0.10F));
                } else {
                    this.penguin.setAIMoveSpeed(Math.max(this.penguin.getAIMoveSpeed() / 2.0F, (float) this.penguin.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() + 1.0F));
                }
            } else if (this.penguin.onGround) {
                this.penguin.setAIMoveSpeed(Math.max(this.penguin.getAIMoveSpeed() / 2.0F, 0.16F));
            }
        }

        @Override
        public void tick() {
            this.updateSpeed();
            if (this.action == Action.MOVE_TO && !this.penguin.getNavigator().noPath()) {
                double x = this.posX - this.penguin.posX;
                double y = this.posY - this.penguin.posY;
                double z = this.posZ - this.penguin.posZ;
                double sqrt = (double) MathHelper.sqrt(x * x + y * y + z * z);
                y /= sqrt;
                float angle = (float) (MathHelper.atan2(z, x) * 57.2957763671875D) - 90.0F;
                this.penguin.rotationYaw = this.limitAngle(this.penguin.rotationYaw, angle, 90.0F);
                this.penguin.renderYawOffset = this.penguin.rotationYaw;
                float movementSpeed = (float) (this.speed * this.penguin.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
                this.penguin.setAIMoveSpeed(this.penguin.getAIMoveSpeed() + (movementSpeed - this.penguin.getAIMoveSpeed()) * 0.125F);
                penguin.motionY += (double) this.penguin.getAIMoveSpeed() * y * 0.1D;
            } else {
                this.penguin.setAIMoveSpeed(0.0F);
            }
        }
    }

    static class PathNavigater extends PathNavigateSwimmer {

        PathNavigater(EntityAdeliePenguin penguin, World world) {
            super(penguin, world);
        }

        @Override
        protected boolean canNavigate() {
            return true;
        }

        @Override
        @Nonnull
        protected PathFinder getPathFinder() {
            return new PathFinder(new WalkAndSwimNodeProcessor());
        }

        @Override
        public boolean canEntityStandOnPos(@Nonnull BlockPos pos) {
            if (this.entity instanceof EntityAdeliePenguin) {
                EntityAdeliePenguin penguin = (EntityAdeliePenguin) this.entity;
                if (penguin.isInWater()) {
                    return this.world.getBlockState(pos).getBlock() == Blocks.WATER;
                }
            }
            return !this.world.getBlockState(pos.down()).isAir();
        }
    }

    static class AITempt extends EntityAIBase {
        private final EntityAdeliePenguin penguin;
        private final double speed;
        private EntityPlayer player;
        private int timer;
        private final Ingredient temptationItems;

        AITempt(EntityAdeliePenguin penguin, double speed, Ingredient temptationItems) {
            this.penguin = penguin;
            this.speed = speed;
            this.temptationItems = temptationItems;
            this.setMutexBits(3);
        }

        @Override
        public boolean shouldExecute() {
            if (this.timer > 0) {
                --this.timer;
                return false;
            } else {
                this.player = this.penguin.world.getClosestPlayerToEntity(this.penguin, 10.0D);
                if (this.player == null) {
                    return false;
                } else {
                    return this.isTemptationItem(this.player.getHeldItemMainhand()) || this.isTemptationItem(this.player.getHeldItemOffhand());
                }
            }
        }

        private boolean isTemptationItem(@Nonnull ItemStack stack) {
            return this.temptationItems.test(stack);
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.shouldExecute();
        }

        @Override
        public void resetTask() {
            this.player = null;
            this.penguin.getNavigator().clearPath();
            this.timer = 100;
        }

        @Override
        public void tick() {
            this.penguin.getLookHelper().setLookPositionWithEntity(this.player, (float) (this.penguin.getHorizontalFaceSpeed() + 20), (float) this.penguin.getVerticalFaceSpeed());
            if (this.penguin.getDistanceSq(this.player) < 6.25D) {
                this.penguin.getNavigator().clearPath();
            } else {
                this.penguin.getNavigator().tryMoveToEntityLiving(this.player, this.speed);
            }

        }
    }

    static class AIWander extends EntityAIWander {

        private AIWander(EntityAdeliePenguin penguin, double speed, int chance) {
            super(penguin, speed, chance);
        }

        @Override
        public boolean shouldExecute() {
            return !this.entity.isInWater() && super.shouldExecute();
        }
    }

    static class AIWanderSwim extends EntityAIWander {

        public AIWanderSwim(EntityCreature creature, double speed, int chance) {
            super(creature, speed, chance);
        }

        @Override
        public boolean shouldExecute() {
            return this.entity.isInWater() && super.shouldExecute();
        }

        @Nullable
        protected Vec3d getPosition() {
            Vec3d randomPos = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);

            for (int var2 = 0; randomPos != null && !this.entity.world.getBlockState(new BlockPos(randomPos)).allowsMovement(this.entity.world, new BlockPos(randomPos), PathType.WATER) && var2++ < 10; randomPos = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7)) {
            }
            return randomPos;
        }
    }

    static class AIGetOutOfWater extends EntityAIMoveToBlock {
        private final EntityAdeliePenguin penguin;

        private AIGetOutOfWater(EntityAdeliePenguin penguin, double speed) {
            super(penguin, penguin.isChild() ? 2.0D : speed, 24);
            this.penguin = penguin;
            this.field_203112_e = -1;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.penguin.isInWater() && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.penguin.world, this.destinationBlock);
        }

        public boolean shouldExecute() {
            if (this.penguin.isChild() && this.penguin.isInWater()) {
                return super.shouldExecute();
            } else {
                return /*!this.penguin.isCatchingFish() &&*/ this.penguin.isInWater() && super.shouldExecute();
            }
        }

        @Override
        public boolean shouldMove() {
            return this.penguin.isInWater();
        }

        @Override
        protected boolean shouldMoveTo(@Nonnull IWorldReaderBase worldReader, @Nonnull BlockPos pos) {
            this.penguin.setPathPriority(PathNodeType.WALKABLE, 10.0F);
            return !worldReader.hasWater(pos);
        }
    }
}