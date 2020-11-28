package tallestegg.guardvillagers.entities;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.PatrolVillageGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.ReturnToVillageGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import tallestegg.guardvillagers.GuardItems;
import tallestegg.guardvillagers.GuardPacketHandler;
import tallestegg.guardvillagers.configuration.GuardConfig;
import tallestegg.guardvillagers.entities.ai.goals.FollowShieldGuards;
import tallestegg.guardvillagers.entities.ai.goals.HelpVillagerGoal;
import tallestegg.guardvillagers.entities.ai.goals.HeroHurtByTargetGoal;
import tallestegg.guardvillagers.entities.ai.goals.HeroHurtTargetGoal;
import tallestegg.guardvillagers.entities.ai.goals.KickGoal;
import tallestegg.guardvillagers.entities.ai.goals.RaiseShieldGoal;
import tallestegg.guardvillagers.entities.ai.goals.RangedCrossbowAttackPassiveGoal;
import tallestegg.guardvillagers.entities.ai.goals.WalkRunWhileReloading;
import tallestegg.guardvillagers.networking.GuardOpenInventoryPacket;

public class GuardEntity extends CreatureEntity implements ICrossbowUser, IRangedAttackMob, IAngerable, IInventoryChangedListener {
    private static final DataParameter<Integer> GUARD_VARIANT = EntityDataManager.createKey(GuardEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> DATA_CHARGING_STATE = EntityDataManager.createKey(GuardEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> KICKING = EntityDataManager.createKey(GuardEntity.class, DataSerializers.BOOLEAN);
    public Inventory guardInventory = new Inventory(6);
    public int kickTicks;
    public int shieldCoolDown;
    public int kickCoolDown;
    public boolean following;
    public int coolDown;
    public PlayerEntity hero;
    private int field_234197_bv_;
    private static final RangedInteger field_234196_bu_ = TickRangeConverter.convertRange(20, 39);
    private UUID field_234198_bw_;

    public GuardEntity(EntityType<? extends GuardEntity> type, World world) {
        super(type, world);
        this.guardInventory.addListener(this);
        this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.guardInventory));
        this.enablePersistence();
        this.setCanPickUpLoot(true);
        if (GuardConfig.GuardsOpenDoors) {
            ((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
        }
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.enablePersistence();
        int type = GuardEntity.getRandomTypeForBiome(world, this.getPosition());
        if (spawnDataIn instanceof GuardEntity.GuardData) {
            type = ((GuardEntity.GuardData) spawnDataIn).variantData;
            spawnDataIn = new GuardEntity.GuardData(type);
        }
        this.setCanPickUpLoot(true);
        if (this.world.rand.nextFloat() < 0.5F) {
            this.setItemStackToSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.SHIELD));
        }
        this.setGuardVariant(type);
        this.setEquipmentBasedOnDifficulty(difficultyIn);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        if (this.getHeldItemOffhand().getUseAction() == UseAction.BLOCK && this.isAggressive() && this.getActiveHand() == Hand.OFF_HAND) {
            return SoundEvents.ITEM_SHIELD_BLOCK;
        } else {
            return SoundEvents.ENTITY_VILLAGER_HURT;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setGuardVariant(compound.getInt("Type"));
        this.kickTicks = compound.getInt("KickTicks");
        this.following = compound.getBoolean("Following");
        this.coolDown = compound.getInt("Cooldown");
        this.shieldCoolDown = compound.getInt("KickCooldown");
        this.kickCoolDown = compound.getInt("ShieldCooldown");
        ListNBT listnbt = compound.getList("Inventory", 10);
        for (int i = 0; i < listnbt.size(); ++i) {
            CompoundNBT compoundnbt = listnbt.getCompound(i);
            int j = compoundnbt.getByte("Slot") & 255;
            this.guardInventory.setInventorySlotContents(j, ItemStack.read(compoundnbt));
        }
        this.readAngerNBT((ServerWorld) this.world, compound);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Type", this.getGuardVariant());
        compound.putInt("KickTicks", this.kickTicks);
        compound.putInt("Cooldown", this.coolDown);
        compound.putInt("ShieldCooldown", this.shieldCoolDown);
        compound.putInt("KickCooldown", this.kickCoolDown);
        compound.putBoolean("Following", this.following);
        ListNBT listnbt = new ListNBT();

        for (int i = 0; i < this.guardInventory.getSizeInventory(); ++i) {
            ItemStack itemstack = this.guardInventory.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                CompoundNBT compoundnbt = new CompoundNBT();
                compoundnbt.putByte("Slot", (byte) i);
                itemstack.write(compoundnbt);
                listnbt.add(compoundnbt);
            }
        }

        compound.put("Inventory", listnbt);
        this.writeAngerNBT(compound);
    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
        if (cause.getTrueSource() instanceof ZombieEntity) {
            if (this.world.getDifficulty() == Difficulty.NORMAL || this.world.getDifficulty() == Difficulty.HARD) {
                if (this.world.getDifficulty() != Difficulty.HARD && this.rand.nextBoolean()) {
                    return;
                }
                ZombieEntity zombie = (ZombieEntity) cause.getTrueSource();
                ZombieVillagerEntity zillager = EntityType.ZOMBIE_VILLAGER.create(world);
                zillager.onInitialSpawn((IServerWorld) world, this.world.getDifficultyForLocation(getPosition()), SpawnReason.CONVERSION, (ILivingEntityData) null, (CompoundNBT) null);
                zillager.copyLocationAndAnglesFrom(this);
                if (this.hasCustomName()) {
                    zillager.setCustomName(zillager.getCustomName());
                    zillager.setCustomNameVisible(this.isCustomNameVisible());
                }

                if (this.isNoDespawnRequired()) {
                    zillager.enablePersistence();
                }
                for (EquipmentSlotType equipmentslottype : EquipmentSlotType.values()) {
                    ItemStack itemstack = this.getItemStackFromSlot(equipmentslottype);
                    if (!itemstack.isEmpty()) {
                        zillager.setItemStackToSlot(equipmentslottype, itemstack.copy());
                        zillager.setDropChance(equipmentslottype, this.getDropChance(equipmentslottype));
                    }
                }
                zillager.setChild(false);
                zillager.setNoAI(this.isAIDisabled());
                zillager.setInvulnerable(this.isInvulnerable());
                zombie.world.addEntity(zillager);
                this.remove(); // oppa gangnam style
                zombie.world.playEvent((PlayerEntity) null, 1026, zombie.getPosition(), 0);
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (this.isKicking()) {
            ((LivingEntity) entityIn).applyKnockback(1.0F, MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F)), (-MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F))));
            this.kickTicks = 10;
            this.world.setEntityState(this, (byte) 4);
            this.faceEntity(entityIn, 90.0F, 90.0F);
        }
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            this.kickTicks = 10;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    public void livingTick() {
        if (this.kickTicks > 0) {
            --this.kickTicks;
        }

        if (this.coolDown > 0) {
            --this.coolDown;
        }
        if (this.kickCoolDown > 0) {
            --this.kickCoolDown;
        }
        if (this.shieldCoolDown > 0) {
            --this.shieldCoolDown;
        }
        this.updateArmSwingProgress();
        super.livingTick();
    }

    @Override
    protected void blockUsingShield(LivingEntity entityIn) {
        super.blockUsingShield(entityIn);
        if (entityIn.getHeldItemMainhand().canDisableShield(this.activeItemStack, this, entityIn)) {
            this.disableShield(true);
        }
    }

    public void disableShield(boolean p_190777_1_) {
        float f = 0.25F + (float) EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;
        if (p_190777_1_) {
            f += 0.75F;
        }
        if (this.rand.nextFloat() < f) {
            this.shieldCoolDown = 100;
            this.resetActiveHand();
            this.world.setEntityState(this, (byte) 30);
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(GUARD_VARIANT, 0);
        this.dataManager.register(DATA_CHARGING_STATE, false);
        this.dataManager.register(KICKING, false);
    }

    public boolean isCharging() {
        return this.dataManager.get(DATA_CHARGING_STATE);
    }

    public void setCharging(boolean p_213671_1_) {
        this.dataManager.set(DATA_CHARGING_STATE, p_213671_1_);
    }

    public boolean isKicking() {
        return this.dataManager.get(KICKING);
    }

    public void setKicking(boolean p_213671_1_) {
        this.dataManager.set(KICKING, p_213671_1_);
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        int i = this.rand.nextInt(2);
        switch (i) {
        case 0:
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
            break;

        case 1:
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.CROSSBOW));
            break;
        }// no more funky if statements
        this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 100.0F;
        this.inventoryHandsDropChances[EquipmentSlotType.OFFHAND.getIndex()] = 100.0F;
        super.setEquipmentBasedOnDifficulty(difficulty); // so guards can spawn with armor
    }

    public int getGuardVariant() {
        return this.dataManager.get(GUARD_VARIANT);
    }

    public void setGuardVariant(int typeId) {
        this.dataManager.set(GUARD_VARIANT, typeId);
    }

    // Credit : the abnormals people for discovering this
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(GuardItems.GUARD_SPAWN_EGG.get());
    }

    // TODO reorganize this stuff
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new ReturnToVillageGoal(this, 0.6D, false));
        this.goalSelector.addGoal(1, new PatrolVillageGoal(this, 0.6D));
        this.goalSelector.addGoal(1, new HeroHurtTargetGoal(this));
        this.goalSelector.addGoal(1, new KickGoal(this));
        this.goalSelector.addGoal(1, new RaiseShieldGoal(this));
        this.goalSelector.addGoal(2, new MoveThroughVillageGoal(this, 0.6D, false, 4, () -> {
            return false;
        }));
        this.goalSelector.addGoal(2, new WalkRunWhileReloading(this, 1.0D));
        this.goalSelector.addGoal(2, new GuardEntity.FollowHeroGoal(this));
        this.goalSelector.addGoal(2, new HeroHurtByTargetGoal(this));
        if (GuardConfig.GuardSurrender) {
            this.goalSelector.addGoal(2, new AvoidEntityGoal<RavagerEntity>(this, RavagerEntity.class, 12.0F, 1.0D, 1.2D) {
                @Override
                public boolean shouldExecute() {
                    return ((GuardEntity) this.entity).getHealth() < 13 && !(entity.getHeldItemOffhand().getItem() instanceof ShieldItem) && super.shouldExecute();
                }

                @Override
                public void startExecuting() {
                    if (((GuardEntity) this.entity).getAttackTarget() == this.avoidTarget) {
                        ((GuardEntity) this.entity).setAttackTarget(null);
                    }
                }
            });
        }
        if (GuardConfig.GuardSurrender) {
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<RavagerEntity>(this, RavagerEntity.class, true) {
                @Override
                public boolean shouldExecute() {
                    return ((GuardEntity) this.goalOwner).getHealth() > 13 && super.shouldExecute();
                }
            });
        }
        if (GuardConfig.GuardFormation) {
            this.goalSelector.addGoal(2, new FollowShieldGuards(this)); // phalanx
        }
        this.goalSelector.addGoal(2, new GuardEntity.DefendVillageGuardGoal(this));
        this.goalSelector.addGoal(2, new HelpVillagerGoal(this));
        if (GuardConfig.GuardsOpenDoors) {
            this.goalSelector.addGoal(3, new OpenDoorGoal(this, true) {
                @Override
                public void startExecuting() {
                    super.startExecuting();
                    this.entity.swingArm(Hand.MAIN_HAND);
                }
            });
        }
        this.goalSelector.addGoal(8, new LookAtGoal(this, AbstractVillagerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F) {
            @Override
            public boolean shouldContinueExecuting() {
                return this.entity.getAttackTarget() == null && super.shouldContinueExecuting();
            }
        });
        this.targetSelector.addGoal(3, new RangedCrossbowAttackPassiveGoal<>(this, 1.0D, 8.0F));
        this.targetSelector.addGoal(3, new GuardEntity.GuardMeleeGoal(this, 0.8D, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, WitchEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IllusionerEntity.class, true));
        if (!GuardConfig.GuardSurrender) {
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, RavagerEntity.class, true));
        }
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this, GuardEntity.class, IronGolemEntity.class)).setCallsForHelp());
        if (GuardConfig.GuardsRunFromPolarBears) {
            this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PolarBearEntity.class, 12.0F, 1.0D, 1.2D));
        }
        if (GuardConfig.AttackAllMobs) {
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MobEntity.class, 5, true, true, (mob) -> {
                return mob instanceof IMob;
            }));
        }
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
        this.targetSelector.addGoal(4, new ResetAngerGoal<>(this, false));
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        this.func_234281_b_(this, 6.0F);
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
        super.getItemStackFromSlot(slotIn);
        switch (slotIn) {
        case CHEST:
            return this.guardInventory.getStackInSlot(1);
        case FEET:
            return this.guardInventory.getStackInSlot(3);
        case HEAD:
            return this.guardInventory.getStackInSlot(0);
        case LEGS:
            return this.guardInventory.getStackInSlot(2);
        case OFFHAND:
            return this.guardInventory.getStackInSlot(4);
        case MAINHAND:
            return this.guardInventory.getStackInSlot(5);
        default:
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {
        super.setItemStackToSlot(slotIn, stack);
        switch (slotIn) {
        case CHEST:
            this.guardInventory.setInventorySlotContents(1, stack);
            break;
        case FEET:
            this.guardInventory.setInventorySlotContents(3, stack);
            break;
        case HEAD:
            this.guardInventory.setInventorySlotContents(0, stack);
            break;
        case LEGS:
            this.guardInventory.setInventorySlotContents(2, stack);
            break;
        case MAINHAND:
            this.guardInventory.setInventorySlotContents(5, stack);
            break;
        case OFFHAND:
            this.guardInventory.setInventorySlotContents(4, stack);
            break;
        }
    }

    @Override
    public ItemStack findAmmo(ItemStack shootable) {
        if (shootable.getItem() instanceof ShootableItem) {
            Predicate<ItemStack> predicate = ((ShootableItem) shootable.getItem()).getAmmoPredicate();
            ItemStack itemstack = ShootableItem.getHeldAmmo(this, predicate);
            return itemstack.isEmpty() ? new ItemStack(Items.ARROW) : itemstack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    public int getKickTicks() {
        return this.kickTicks;
    }

    @Override
    public boolean canAttack(EntityType<?> typeIn) {
        if (this.following && typeIn == EntityType.PLAYER || this.hero != null && typeIn == EntityType.PLAYER) {
            return false;
        } else {
            return super.canAttack(typeIn);
        }
    }

    /**
     * Credit - SmellyModder for Biome Specific Textures
     */
    public static int getRandomTypeForBiome(IWorld world, BlockPos pos) {
        Biome biome = world.getBiome(pos);
        if (biome.getCategory() == Category.PLAINS) {
            return 0;
        }

        if (biome.getCategory() == Category.DESERT) {
            return 1;
        }

        if (biome.getCategory() == Category.SAVANNA) {
            return 2;
        }

        if (biome.getCategory() == Category.SWAMP) {
            return 3;
        }

        if (biome.getCategory() == Category.JUNGLE) {
            return 4;
        }

        if (biome.getCategory() == Category.TAIGA) {
            return 5;
        }

        if (biome.getCategory() == Category.ICY) {
            return 6;
        } else {
            return 0;
        }
    }

    public void updateRidden() {
        super.updateRidden();
        if (this.getRidingEntity() instanceof CreatureEntity) {
            CreatureEntity creatureentity = (CreatureEntity) this.getRidingEntity();
            this.renderYawOffset = creatureentity.renderYawOffset;
        }
    }

    @Override
    public void func_230283_U__() {
        this.idleTime = 0;
    }

    @Override
    public void func_230284_a_(LivingEntity arg0, ItemStack arg1, ProjectileEntity arg2, float arg3) {
        this.func_234279_a_(this, arg0, arg2, arg3, 1.6F);
    }

    @Override
    protected void constructKnockBackVector(LivingEntity entityIn) {
        if (this.isKicking()) {
            this.setKicking(false);
        }
        super.constructKnockBackVector(this);
    }

    @Override
    protected ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack heldStack = player.getHeldItem(hand);
        if (player.isCrouching() && this.isServerWorld()) {
            this.openGui((ServerPlayerEntity) player);
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        if (heldStack.getItem().isFood() && this.getHealth() < this.getMaxHealth()) {
            this.heal(heldStack.getItem().getFood().getHealing());
            this.playHealEffect();
            if (!player.abilities.isCreativeMode)
                heldStack.shrink(1);
            return ActionResultType.SUCCESS;
        }
        if (player.isPotionActive(Effects.HERO_OF_THE_VILLAGE)) {
            this.playSound(SoundEvents.ENTITY_VILLAGER_CELEBRATE, 1.0F, 1.0F);
            this.following = !this.following;
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
        int i = inventorySlot - 400;
        if (i >= 0 && i < 2 && i < this.guardInventory.getSizeInventory()) {
            if (i == 0) {
                return false;
            } else if (i != 1 || itemStackIn.getItem() instanceof ArmorItem) {
                this.guardInventory.setInventorySlotContents(i, itemStackIn);
                return true;
            } else {
                return false;
            }
        } else {
            int j = inventorySlot - 500 + 2;
            if (j >= 2 && j < this.guardInventory.getSizeInventory()) {
                this.guardInventory.setInventorySlotContents(j, itemStackIn);
                return true;
            } else {
                return false;
            }
        }
    }

    protected void playHealEffect() {
        IParticleData iparticledata = ParticleTypes.HAPPY_VILLAGER;
        for (int i = 0; i < 7; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.addParticle(iparticledata, this.getPosX() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), this.getPosY() + 0.5D + (double) (this.rand.nextFloat() * this.getHeight()),
                    this.getPosZ() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), d0, d1, d2);
        }
    }

    public static String getNameByType(int id) {
        switch (id) {
        case 0:
            return "plains";
        case 1:
            return "desert";
        case 2:
            return "savanna";
        case 3:
            return "swamp";
        case 4:
            return "jungle";
        case 5:
            return "taiga";
        case 6:
            return "snow";
        }
        return "";
    }

    @Override
    public void onInventoryChanged(IInventory invBasic) {
    }

    @Override
    public UUID getAngerTarget() {
        return this.field_234198_bw_;
    }

    @Override
    public int getAngerTime() {
        return this.field_234197_bv_;
    }

    @Override
    public void setAngerTarget(UUID arg0) {
        this.field_234198_bw_ = arg0;
    }

    @Override
    public void setAngerTime(int arg0) {
        this.field_234197_bv_ = arg0;
    }

    @Override
    public void func_230258_H__() {
        this.setAngerTime(field_234196_bu_.func_233018_a_(this.rand));
    }

    public void openGui(ServerPlayerEntity player) {
        if (player.openContainer != player.container) {
            player.closeScreen();
        }
        player.getNextWindowId();
        GuardPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new GuardOpenInventoryPacket(player.currentWindowId, this.guardInventory.getSizeInventory(), this.getEntityId()));
        player.openContainer = new GuardContainer(player.currentWindowId, player.inventory, this.guardInventory, this);
        player.openContainer.addListener(player);
        MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(player, player.openContainer));
    }

    /*
     * protected void registerAttributes() { super.registerAttributes();
     * this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(25.0D);
     * this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
     * this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
     * this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
     * this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
     * .setBaseValue(1.0D); }
     */

    // field_233821_d_ = movement speed
    // field_233818_a_ = health
    // field_233823_f_ = attack damage
    // field_233819_b_ = follow range
    // field_233826_i_ = armor

    public static AttributeModifierMap.MutableAttribute func_234200_m_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 25.0D)
                .createMutableAttribute(Attributes.ARMOR, 1.0D);
    }

    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.Direction facing) {
        if (this.isAlive() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemHandler != null)
            return itemHandler.cast();
        return super.getCapability(capability, facing);
    }

    public static class GuardData implements ILivingEntityData {
        public final int variantData;

        public GuardData(int type) {
            this.variantData = type;
        }
    }

    public static class DefendVillageGuardGoal extends TargetGoal {
        protected final GuardEntity guard;
        protected LivingEntity villageAggressorTarget;
        protected final EntityPredicate field_223190_c = (new EntityPredicate()).setDistance(64.0D);

        public DefendVillageGuardGoal(GuardEntity guardIn) {
            super(guardIn, false, true);
            this.guard = guardIn;
            this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        public boolean shouldExecute() {
            AxisAlignedBB axisalignedbb = this.guard.getBoundingBox().grow(10.0D, 8.0D, 10.0D);
            List<LivingEntity> list = this.guard.world.getTargettableEntitiesWithinAABB(VillagerEntity.class, this.field_223190_c, this.guard, axisalignedbb);
            List<PlayerEntity> list1 = this.guard.world.getTargettablePlayersWithinAABB(this.field_223190_c, this.guard, axisalignedbb);

            for (LivingEntity livingentity : list) {
                VillagerEntity villagerentity = (VillagerEntity) livingentity;

                for (PlayerEntity playerentity : list1) {
                    int i = villagerentity.getPlayerReputation(playerentity);
                    if (i <= -100) {
                        this.villageAggressorTarget = playerentity;
                    }
                }
            }

            if (this.villageAggressorTarget == null) {
                return false;
            } else {
                return !(this.villageAggressorTarget instanceof PlayerEntity) || !this.villageAggressorTarget.isSpectator() && !((PlayerEntity) this.villageAggressorTarget).isCreative();
            }
        }

        public void startExecuting() {
            this.guard.setAttackTarget(this.villageAggressorTarget);
            super.startExecuting();
        }
    }

    public static class FollowHeroGoal extends Goal {
        public final GuardEntity guard;
        protected final EntityPredicate whateverthisis = (new EntityPredicate()).setDistance(64.0D);

        public FollowHeroGoal(GuardEntity mob) {
            guard = mob;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public void startExecuting() {
            super.startExecuting();
            guard.following = true;
        }

        @Override
        public boolean shouldContinueExecuting() {
            super.shouldContinueExecuting();
            return guard.following;
        }

        @Override
        public boolean shouldExecute() {
            List<LivingEntity> list = this.guard.world.getEntitiesWithinAABB(PlayerEntity.class, this.guard.getBoundingBox().grow(10.0D));
            if (!list.isEmpty()) {
                for (LivingEntity mob : list) {
                    PlayerEntity player = (PlayerEntity) mob;
                    if (!player.isInvisible() && player.isPotionActive(Effects.HERO_OF_THE_VILLAGE)) {
                        guard.hero = player; // this makes it so guards stan the player
                        if (guard.following) {
                            guard.getNavigator().tryMoveToEntityLiving(guard.hero, 0.9D);
                        }
                        return guard.following;
                    }
                }
            }
            return false;
        }

        @Override
        public void resetTask() {
            this.guard.getNavigator().clearPath();
            guard.following = false;
            guard.hero = null;
        }
    }

    public class GuardMeleeGoal extends MeleeAttackGoal {

        public final GuardEntity guard;

        public GuardMeleeGoal(GuardEntity guard, double speedIn, boolean useLongMemory) {
            super(guard, speedIn, useLongMemory);
            this.guard = guard;
        }

        @Override
        public boolean shouldExecute() {
            return !(this.guard.getHeldItemMainhand().getItem().isCrossbow(guard.getHeldItemMainhand())) && this.guard.getAttackTarget() != null && super.shouldExecute();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting() && this.guard.getAttackTarget() != null;
        }

        @Override
        public void tick() {
            if (this.guard.getAttackTarget() != null) {
                super.tick();
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return super.getAttackReachSqr(attackTarget) * 3.55D;
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.field_234037_i_ <= 0) {
                this.func_234039_g_();
                this.guard.swingArm(Hand.MAIN_HAND);
                this.guard.attackEntityAsMob(enemy);
                this.guard.resetActiveHand();
                if (guard.shieldCoolDown == 0) {
                    this.guard.shieldCoolDown = 8;
                }
            }
        }
    }
}