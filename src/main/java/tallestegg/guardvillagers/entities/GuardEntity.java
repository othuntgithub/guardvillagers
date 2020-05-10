package tallestegg.guardvillagers.entities;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraftforge.fml.ModList;
import tallestegg.guardvillagers.configuration.GuardConfig;
import tallestegg.guardvillagers.entities.goals.HelpVillagerGoal;
import tallestegg.guardvillagers.entities.goals.RangedCrossbowAttackPassiveGoal;

public class GuardEntity extends CreatureEntity implements ICrossbowUser, IRangedAttackMob
{ 
	private static final DataParameter<Integer> GUARD_VARIANT = EntityDataManager.createKey(GuardEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> DATA_CHARGING_STATE = EntityDataManager.createKey(GuardEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> KICKING = EntityDataManager.createKey(GuardEntity.class, DataSerializers.BOOLEAN);
    private final RangedCrossbowAttackPassiveGoal<GuardEntity> crossBowAttack = new RangedCrossbowAttackPassiveGoal<GuardEntity>(this, 1.0D, 8.0F);
    private final MeleeAttackGoal meleeAttack = new MeleeAttackGoal(this, 1.0D, true) {
    	@Override
        public void resetTask() {
           super.resetTask();
           GuardEntity.this.setAggroed(false);
        }

        @Override
        public void startExecuting() {
           super.startExecuting();
           GuardEntity.this.setAggroed(true);
        }

		@Override
		protected double getAttackReachSqr(LivingEntity attackTarget) {
			return super.getAttackReachSqr(attackTarget) * 3.55D;
		}
	};
	 
	public GuardEntity(EntityType<? extends GuardEntity> type, World world)
	{
		super(type, world);
		if (GuardConfig.GuardsOpenDoors) {
			((GroundPathNavigator)this.getNavigator()).setBreakDoors(true);
		}
		this.setCombatTask();
		this.enablePersistence();
	}

	@Override
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) 
	{
	  int type = GuardEntity.getRandomTypeForBiome(world, this.getPosition());
	  if (spawnDataIn instanceof GuardEntity.GuardData) 
	  {
        type = ((GuardEntity.GuardData)spawnDataIn).variantData;
	    spawnDataIn = new GuardEntity.GuardData(type);
	  }
       this.setCombatTask();
       if (this.world.rand.nextFloat() < 0.5F) {
           this.setItemStackToSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.SHIELD));
  	   } 
	   this.setGuardVariant(type);
	   this.setEquipmentBasedOnDifficulty(difficultyIn);
	   return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}
	
	/* this method sets the entities 
	   combat task, if the entity has a
       crossbow, it will use the crossbow attack
       goal, if it uses any other item it will use the
       melee attack goal */
	public void setCombatTask() 
	{
      this.targetSelector.removeGoal(this.meleeAttack);
      this.targetSelector.removeGoal(this.crossBowAttack);

      ItemStack itemstack = this.getHeldItem(ProjectileHelper.getHandWith(this, Items.CROSSBOW));
      if (itemstack.getItem() instanceof CrossbowItem) 
      {
       this.targetSelector.addGoal(3, this.crossBowAttack);
      }
      else 
      {
        this.targetSelector.addGoal(3, this.meleeAttack);
      }
    }
	
	protected SoundEvent getAmbientSound() 
	{
	  return SoundEvents.ENTITY_VILLAGER_AMBIENT;
	}

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) 
	{
	  return SoundEvents.ENTITY_VILLAGER_HURT;
	}

	protected SoundEvent getDeathSound() 
	{
	  return SoundEvents.ENTITY_VILLAGER_DEATH;
	}
	   
	@Override
	public void readAdditional(CompoundNBT compound) 
	{
		super.readAdditional(compound);
		this.setGuardVariant(compound.getInt("Type"));
		this.setCombatTask();
	}
	
	
	public void livingTick() 
	{
		if (this.ticksExisted % 100 == 0 && GuardConfig.GuardHealthRegen == true) 
		{
	      this.heal(2.0F);	
		}
	    this.updateArmSwingProgress();
	    this.raiseShield();
	    super.livingTick();
	}
	
	public void raiseShield()
	{
		if (this.getHeldItemOffhand().getItem() instanceof ShieldItem && this.getAttackTarget() != null && this.getAttackTarget().getDistance(this) <= 2.0D
				|| this.getHeldItemOffhand().getItem() instanceof ShieldItem && this.getAttackTarget() != null && this.getAttackTarget() instanceof RavagerEntity
				|| this.getHeldItemOffhand().getItem() instanceof ShieldItem && this.getAttackTarget() != null && this.getAttackTarget() instanceof RavagerEntity && this.getHeldItemMainhand().getItem() instanceof CrossbowItem && this.getAttackTarget().getDistance(this) <= 2.0D
				|| this.getHeldItemOffhand().getItem() instanceof ShieldItem && this.getAttackTarget() != null && this.getAttackTarget() instanceof CreeperEntity
				|| this.getHeldItemOffhand().getItem() instanceof ShieldItem && this.getAttackTarget() != null && this.getAttackTarget() instanceof IRangedAttackMob && this.getAttackTarget().getDistance(this) >= 5.0D && !(this.getHeldItemMainhand().getItem() instanceof CrossbowItem))
		{
	    	this.setActiveHand(Hand.OFF_HAND);
		    this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3F);
	    }
		if (this.getHeldItemOffhand().getItem() instanceof ShieldItem && !this.isAggressive())
		{
			this.resetActiveHand();
		    this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		}
	}
	
	public void kick(float f1)
	{
		this.setKicking(true);
		if (this.isKicking())
		{
			LivingEntity attacker = this.getAttackTarget();
			this.attackEntityAsMob(attacker);
			double distance = this.getDistance(attacker);
			attacker.knockBack(attacker, f1, distance, distance);
		}
	}

	@Override
	protected void registerData() 
	{
		super.registerData();
		this.dataManager.register(GUARD_VARIANT, 0);
		this.dataManager.register(DATA_CHARGING_STATE, false);
		this.dataManager.register(KICKING, false);
	}

    public boolean isCharging() 
    {
	  return this.dataManager.get(DATA_CHARGING_STATE);
    }

    public void setCharging(boolean p_213671_1_) 
    {
	   this.dataManager.set(DATA_CHARGING_STATE, p_213671_1_);
    }
    
    public boolean isKicking() 
    {
	  return this.dataManager.get(KICKING);
    }

    public void setKicking(boolean p_213671_1_) 
    {
	   this.dataManager.set(KICKING, p_213671_1_);
    }
	   
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) 
	{
	  int i = this.rand.nextInt(2);
		if (i == 0) 
		{
	      this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
	    } 
		 else 
	     {
	       this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.CROSSBOW));
	     }
	   this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 50.0F;
	}
	
	public int getGuardVariant()
    {
		return this.dataManager.get(GUARD_VARIANT);
	}
	
	public void setGuardVariant(int typeId)
	{
		this.dataManager.set(GUARD_VARIANT, typeId);
	}
	
	protected void registerGoals() 
	{
		  this.goalSelector.addGoal(1, new SwimGoal(this));
	      this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
	      this.goalSelector.addGoal(1, new MoveTowardsVillageGoal(this, 0.6D));
		  this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
	      this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6D));
	      this.goalSelector.addGoal(2, new GuardEntity.DefendVillageGuardGoal(this));
	      this.goalSelector.addGoal(2, new HelpVillagerGoal(this));
	      this.goalSelector.addGoal(2, new MoveThroughVillageGoal(this, 0.6D, false, 4, () -> {
	          return false;
	       }));
	      this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
	      this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
	      if (GuardConfig.GuardSurrender) {
		      this.goalSelector.addGoal(2, new AvoidEntityGoal<RavagerEntity>(this, RavagerEntity.class,  12.0F, 1.0D, 1.2D) {
					@Override
					public boolean shouldExecute() {
						return ((GuardEntity)this.entity).getHealth() < 13 && !(entity.getHeldItemOffhand().getItem() instanceof ShieldItem) && super.shouldExecute();						}
				   });	      
		         }
	      if (GuardConfig.GuardSurrender) {
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<RavagerEntity>(this, RavagerEntity.class, true) {
				@Override
				public boolean shouldExecute() {
					return ((GuardEntity)this.goalOwner).getHealth() > 13 && super.shouldExecute();				}
			});
	      }
	      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, true));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, true));
	      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, WitchEntity.class, true));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IllusionerEntity.class, true));
	      if (!GuardConfig.GuardSurrender) 
	      {
	        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, RavagerEntity.class, true));
	      }
	      this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, GuardEntity.class, IronGolemEntity.class)).setCallsForHelp());
	      if (GuardConfig.GuardsRunFromPolarBears) 
	      {
	         this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PolarBearEntity.class,  12.0F, 1.0D, 1.2D));
	      }
	      if (GuardConfig.AttackAllMobs)
	      {
	    	  this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MobEntity.class, 5, true, true, (mob) ->
	  		{
	  			return mob instanceof IMob;
	  		}));
	      }
	      if (ModList.get().isLoaded("quark")) {
		    this.goalSelector.addGoal(2, new TemptGoal(this, 0.6, Ingredient.fromItems(Items.EMERALD_BLOCK), false));
	      }
	 }
	
	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) 
	{
		 Hand hand = ProjectileHelper.getHandWith(this, Items.CROSSBOW);
	      ItemStack itemstack = this.getHeldItem(hand);
	      if (this.isHolding(Items.CROSSBOW)) {
	         CrossbowItem.fireProjectiles(this.world, this, hand, itemstack, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
	      }

	      this.idleTime = 0;
	}

	
   @Override
   public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack)
   {
	  super.setItemStackToSlot(slotIn, stack);
	  if (!this.world.isRemote) 
	  {
		this.setCombatTask();
	  }
    }
   
   
	@Override
	public void shoot(LivingEntity target, ItemStack p_213670_2_, IProjectile projectile, float projectileAngle) 
	{
	      Entity entity = (Entity)projectile;
	      double d0 = target.posX - this.posX;
	      double d1 = target.posZ - this.posZ;
	      double d2 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1);
	      double d3 = target.getBoundingBox().minY + (double)(target.getHeight() / 3.0F) - entity.posY + d2 * (double)0.2F;
	      Vector3f vector3f = this.aim(new Vec3d(d0, d3, d1), projectileAngle);
	      projectile.shoot((double)vector3f.getX(), (double)vector3f.getY(), (double)vector3f.getZ(), 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
	      this.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	}
	
	private Vector3f aim(Vec3d p_213673_1_, float p_213673_2_) {
	      Vec3d vec3d = p_213673_1_.normalize();
	      Vec3d vec3d1 = vec3d.crossProduct(new Vec3d(0.0D, 1.0D, 0.0D));
	      if (vec3d1.lengthSquared() <= 1.0E-7D) {
	         vec3d1 = vec3d.crossProduct(this.getUpVector(1.0F));
	      }

	      Quaternion quaternion = new Quaternion(new Vector3f(vec3d1), 90.0F, true);
	      Vector3f vector3f = new Vector3f(vec3d);
	      vector3f.func_214905_a(quaternion);
	      Quaternion quaternion1 = new Quaternion(vector3f, p_213673_2_, true);
	      Vector3f vector3f1 = new Vector3f(vec3d);
	      vector3f1.func_214905_a(quaternion1);
	      return vector3f1;
	   }
	
	 @Override
	 public ItemStack findAmmo(ItemStack shootable) 
	 {
	      if (shootable.getItem() instanceof ShootableItem) 
	      {
	         Predicate<ItemStack> predicate = ((ShootableItem)shootable.getItem()).getAmmoPredicate();
	         ItemStack itemstack = ShootableItem.getHeldAmmo(this, predicate);
	         return itemstack.isEmpty() ? new ItemStack(Items.ARROW) : itemstack;
	      }  
	       else 
	       {
	         return ItemStack.EMPTY;
	       }
	   }
	
	@Override
	public void writeAdditional(CompoundNBT compound) 
	{
		super.writeAdditional(compound);
		compound.putInt("Type", this.getGuardVariant());
	}
	
	public static int getRandomTypeForBiome(IWorld world, BlockPos pos) {
		    Biome biome = world.getBiome(pos);
			if(biome.getCategory() == Category.PLAINS) 
			{
					return 0;
			}
			
			if (biome.getCategory() == Category.DESERT) 
			{
					return 1;
			}
			
			if (biome.getCategory() == Category.SAVANNA) 
			{
					return 2;
			}
			
			if (biome.getCategory() == Category.SWAMP) 
			{
					return 3;
			}
			
			if (biome.getCategory() == Category.JUNGLE) 
			{
					return 4;
			}
			
			if (biome.getCategory() == Category.TAIGA) 
			{
					return 5;
			}
			
			if (biome.getCategory() == Category.ICY) 
			{
					return 6;
			}
			else 
			{
			  return 0;
			}
	}

	public void updateRidden() {
		super.updateRidden();
		if (this.getRidingEntity() instanceof CreatureEntity) {
			CreatureEntity creatureentity = (CreatureEntity)this.getRidingEntity();
			this.renderYawOffset = creatureentity.renderYawOffset;
		}
	}

		@Override
		protected void constructKnockBackVector(LivingEntity entityIn)
		{
			if (this.isKicking())
			{
				this.setKicking(false);
			}
			super.constructKnockBackVector(this);
		}
	   
	   public boolean processInteract(PlayerEntity player, Hand hand)
		{
		  ItemStack heldStack = player.getHeldItem(hand);
		  if (!isAggressive() && !(this.getAttackTarget() instanceof PlayerEntity)) {
		  if (!(heldStack.getItem() instanceof ShieldItem) && !(heldStack.getItem().isFood()) && player.isSneaking() && heldStack.getItem() != Items.AIR)
		  {
			this.entityDropItem(this.getHeldItemMainhand());
		    this.setItemStackToSlot(EquipmentSlotType.MAINHAND, heldStack.copy());
		    if (!player.abilities.isCreativeMode)
		    heldStack.shrink(1);
		  }
		  
		  if (heldStack.getItem() instanceof ShieldItem && player.isSneaking())
		  {
			this.entityDropItem(this.getHeldItemOffhand());
		    this.setItemStackToSlot(EquipmentSlotType.OFFHAND, heldStack.copy());
		    if (!player.abilities.isCreativeMode)
		    heldStack.shrink(1);
		  }
		  
		  if (heldStack.getItem().isFood() && this.getHealth() < this.getMaxHealth()) 
		  {
		    if (!player.abilities.isCreativeMode) 
		    {
		      heldStack.shrink(1);
		    }

		    this.heal((float)heldStack.getItem().getFood().getHealing());
		    this.playHealEffect(true);
		   }
		    return true;
		  } else {
		    return super.processInteract(player, hand);
		  }
		}
	   
	   protected void playHealEffect(boolean play) {
		      IParticleData iparticledata = ParticleTypes.HAPPY_VILLAGER;
		      for(int i = 0; i < 7; ++i) {
		         double d0 = this.rand.nextGaussian() * 0.02D;
		         double d1 = this.rand.nextGaussian() * 0.02D;
		         double d2 = this.rand.nextGaussian() * 0.02D;
		         this.world.addParticle(iparticledata, this.posX + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), this.posY + 0.5D + (double)(this.rand.nextFloat() * this.getHeight()), this.posZ + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), d0, d1, d2);
		      }
		   }
	   
	public static String getNameByType(int id) 
	{
		switch(id) 
		{
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
	
	
	protected void registerAttributes() 
	{
	      super.registerAttributes();
	      this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(25.0D);
	      this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5.0D);
	      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
	      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
	      this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
	}
	
	public static class GuardData implements ILivingEntityData 
	{
		public final int variantData;

		public GuardData(int type) 
		{
			this.variantData = type;
		}
	}

	/**
	 * Credit - SmellyModder for Biome Specific Textures
	 */
	
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

		      for(LivingEntity livingentity : list) {
		         VillagerEntity villagerentity = (VillagerEntity)livingentity;

		         for(PlayerEntity playerentity : list1) {
		            int i = villagerentity.getPlayerReputation(playerentity);
		            if (i <= -100) {
		               this.villageAggressorTarget = playerentity;
		            }
		         }
		      }

		      if (this.villageAggressorTarget == null) {
		         return false;
		      } else {
		         return !(this.villageAggressorTarget instanceof PlayerEntity) || !this.villageAggressorTarget.isSpectator() && !((PlayerEntity)this.villageAggressorTarget).isCreative();
		      }
		   }

		   public void startExecuting() {
		      this.guard.setAttackTarget(this.villageAggressorTarget);
		      super.startExecuting();
		   }
		}
}